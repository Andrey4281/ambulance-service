#!/bin/bash
cd kafka
kubectl apply -f kafka-namespace.yaml
helm -n kafka install kafka-cluster bitnami/kafka
helm install -f cowl-values.yaml kowl cloudhut/kowl -n kafka
cd ../appeal
kubectl apply -f appeal-db-namespace.yaml
kubectl apply -f appeal-db-secret.yaml
helm -n appeal-db install appeal-database -f appeal-postgresql-values.yaml bitnami/postgresql
cd ../doctor
kubectl apply -f doctor-db-namespace.yaml
kubectl apply -f doctor-db-secret.yaml
helm -n doctor-db install doctor-database -f doctor-postgresql-values.yaml bitnami/postgresql
cd ../nurse
kubectl apply -f nurse-db-namespace.yaml
kubectl apply -f nurse-db-secret.yaml
helm -n nurse-db install nurse-database -f nurse-postgresql-values.yaml bitnami/postgresql
cd ../prometheus
kubectl apply -f monitoring-namespace.yaml
helm -n monitoring install stack prometheus-community/kube-prometheus-stack -f stack-values.yaml
cd ../helm
helm install appeal-service backend
helm install -f doctor-values.yaml doctor-service backend
helm install -f nurse-values.yaml nurse-service backend
cd ../gateway
kubectl apply -f gateway-namespace.yaml
kubectl apply -f gateway-config.yaml
kubectl apply -f gateway-deployment.yaml
