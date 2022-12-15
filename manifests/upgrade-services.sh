#!/bin/bash
cd helm
helm upgrade appeal-service backend
helm upgrade -f doctor-values.yaml doctor-service backend
helm upgrade -f nurse-values.yaml nurse-service backend