#!/bin/bash

# LocalStack management script
set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

LOCALSTACK_ENDPOINT="http://localhost:4566"

echo -e "${BLUE}🔍 Checking LocalStack status...${NC}"

# Check if LocalStack is already running
if curl -s "$LOCALSTACK_ENDPOINT" > /dev/null 2>&1; then
    echo -e "${GREEN}✅ LocalStack is already running at $LOCALSTACK_ENDPOINT${NC}"
    echo -e "${YELLOW}📋 You can use the existing LocalStack instance${NC}"
    exit 0
fi

# Check if LocalStack container exists but is stopped
if docker ps -a --format "table {{.Names}}" | grep -q "localstack"; then
    echo -e "${YELLOW}🔄 Found stopped LocalStack container, starting it...${NC}"
    docker start localstack

    # Wait for LocalStack to be ready
    echo -e "${YELLOW}⏳ Waiting for LocalStack to be ready...${NC}"
    timeout 60 bash -c 'until curl -s http://localhost:4566 > /dev/null; do sleep 2; done'

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ LocalStack started successfully${NC}"
    else
        echo -e "${RED}❌ Failed to start LocalStack${NC}"
        exit 1
    fi
else
    echo -e "${YELLOW}🚀 No LocalStack found, starting new instance...${NC}"

    # Start LocalStack with Docker
    docker run -d \
        --name localstack \
        -p 4566:4566 \
        -e SERVICES=s3,dynamodb,rds,ecs,ecr,lambda,apigateway,cloudwatch \
        -e DEBUG=1 \
        -e AWS_DEFAULT_REGION=us-east-1 \
        -e EDGE_PORT=4566 \
        -e LAMBDA_EXECUTOR=docker \
        -e LAMBDA_REMOTE_DOCKER=false \
        -e DOCKER_HOST=unix:///var/run/docker.sock \
        -v "${TMPDIR:-/tmp}/localstack:/tmp/localstack" \
        -v "/var/run/docker.sock:/var/run/docker.sock" \
        localstack/localstack:latest

    # Wait for LocalStack to be ready
    echo -e "${YELLOW}⏳ Waiting for LocalStack to be ready...${NC}"
    timeout 60 bash -c 'until curl -s http://localhost:4566 > /dev/null; do sleep 2; done'

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ LocalStack started successfully${NC}"
    else
        echo -e "${RED}❌ Failed to start LocalStack${NC}"
        exit 1
    fi
fi

echo -e "${BLUE}📊 LocalStack Information:${NC}"
echo -e "${YELLOW}Endpoint:${NC} $LOCALSTACK_ENDPOINT"
echo -e "${YELLOW}Services:${NC} S3, DynamoDB, RDS, ECS, ECR, Lambda, API Gateway, CloudWatch"
echo -e "${YELLOW}Region:${NC} us-east-1"
echo -e "${YELLOW}Credentials:${NC} test/test"
