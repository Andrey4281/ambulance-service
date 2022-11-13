#!/bin/bash
kubectl --namespace appeal port-forward svc/appeal-database-postgresql 5434:5432
