#!/bin/bash

# Deploy script for RentApp with LocalStack
set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
PROJECT_NAME="rentapp"
ENVIRONMENT="local"
LOCALSTACK_ENDPOINT="http://localhost:4566"

echo -e "${BLUE}🚀 Starting RentApp deployment to LocalStack...${NC}"

# Function to check if LocalStack is running
check_localstack() {
    echo -e "${YELLOW}📋 Checking LocalStack status...${NC}"

    if curl -s "$LOCALSTACK_ENDPOINT" > /dev/null; then
        echo -e "${GREEN}✅ LocalStack is running${NC}"
    else
        echo -e "${RED}❌ LocalStack is not running. Please start it first:${NC}"
        echo "docker-compose up localstack -d"
        exit 1
    fi
}

# Function to build Docker image
build_image() {
    echo -e "${YELLOW}🔨 Building Docker image...${NC}"

    docker build -t "$PROJECT_NAME:$ENVIRONMENT" .

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Docker image built successfully${NC}"
    else
        echo -e "${RED}❌ Failed to build Docker image${NC}"
        exit 1
    fi
}

# Function to initialize Terraform
init_terraform() {
    echo -e "${YELLOW}🏗️  Initializing Terraform...${NC}"

    cd terraform

    # Set environment variables for LocalStack
    export AWS_ACCESS_KEY_ID=test
    export AWS_SECRET_ACCESS_KEY=test
    export AWS_DEFAULT_REGION=us-east-1
    export TF_VAR_project_name=$PROJECT_NAME
    export TF_VAR_environment=$ENVIRONMENT

    terraform init

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Terraform initialized successfully${NC}"
    else
        echo -e "${RED}❌ Failed to initialize Terraform${NC}"
        exit 1
    fi

    cd ..
}

# Function to apply Terraform configuration
apply_terraform() {
    echo -e "${YELLOW}🚀 Applying Terraform configuration...${NC}"

    cd terraform

    terraform plan -out=tfplan

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Terraform plan created successfully${NC}"
    else
        echo -e "${RED}❌ Failed to create Terraform plan${NC}"
        exit 1
    fi

    terraform apply tfplan

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Terraform configuration applied successfully${NC}"
    else
        echo -e "${RED}❌ Failed to apply Terraform configuration${NC}"
        exit 1
    fi

    cd ..
}

# Function to deploy application
deploy_application() {
    echo -e "${YELLOW}📦 Deploying application...${NC}"

    # Stop existing containers
    docker-compose down

    # Start services
    docker-compose up -d

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Application deployed successfully${NC}"
    else
        echo -e "${RED}❌ Failed to deploy application${NC}"
        exit 1
    fi
}

# Function to run health checks
health_check() {
    echo -e "${YELLOW}🏥 Running health checks...${NC}"

    # Wait for application to start
    sleep 30

    # Check application health
    if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo -e "${GREEN}✅ Application is healthy${NC}"
    else
        echo -e "${RED}❌ Application health check failed${NC}"
        exit 1
    fi

    # Check LocalStack services
    if curl -s "$LOCALSTACK_ENDPOINT/health" > /health" > /dev/null; then
        echo -e "${GREEN}✅ LocalStack is healthy${NC}"
    else
        echo -e "${RED}❌ LocalStack health check failed${NC}"
        exit 1
    fi
}

# Function to display deployment info
show_info() {
    echo -e "${BLUE}📊 Deployment Information:${NC}"
    echo -e "${YELLOW}Application URL:${NC} http://localhost:8080"
    echo -e "${YELLOW}LocalStack URL:${NC} $LOCALSTACK_ENDPOINT"
    echo -e "${YELLOW}PostgreSQL:${NC} localhost:5432"
    echo -e "${YELLOW}Redis:${NC} localhost:6379"
    echo ""
    echo -e "${GREEN}🎉 Deployment completed successfully!${NC}"
}

# Main deployment flow
main() {
    check_localstack
    build_image
    init_terraform
    apply_terraform
    deploy_application
    health_check
    show_info
}

# Run main function
main "$@"
