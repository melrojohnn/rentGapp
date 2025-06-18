#!/bin/bash

# Script para testar o cadastro de uma order
# Certifique-se de que a aplicação está rodando na porta 8099

echo "=== Testando Cadastro de Order ==="
echo ""

# 1. Primeiro, vamos verificar se a aplicação está rodando
echo "1. Verificando se a aplicação está rodando..."
response=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8099/v1/order)

if [ "$response" = "200" ] || [ "$response" = "404" ]; then
    echo "✅ Aplicação está rodando (Status: $response)"
else
    echo "❌ Aplicação não está respondendo. Certifique-se de que está rodando na porta 8099"
    exit 1
fi

echo ""

# 2. Testar criação de uma order
echo "2. Testando criação de uma order..."

# Dados para criar uma order
# Assumindo que temos:
# - Customer ID: 1 (do script populate_data.sql)
# - Plan ID: 1 (do script populate_data.sql)
# - Equipment IDs: [1, 2] (laptops do script populate_data.sql)

curl -X POST http://localhost:8099/v1/order \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "customerId": 1,
    "planId": 1,
    "equipmentIds": [1, 2]
  }' \
  -w "\nStatus: %{http_code}\n" \
  -s

echo ""
echo ""

# 3. Listar todas as orders para verificar se foi criada
echo "3. Listando todas as orders..."
curl -X GET http://localhost:8099/v1/order \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -w "\nStatus: %{http_code}\n" \
  -s

echo ""
echo ""

# 4. Buscar a order específica (assumindo ID = 1)
echo "4. Buscando order com ID = 1..."
curl -X GET http://localhost:8099/v1/order/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -w "\nStatus: %{http_code}\n" \
  -s

echo ""
echo "=== Teste Concluído ==="
