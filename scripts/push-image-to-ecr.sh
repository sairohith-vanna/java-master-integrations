#!/bin/bash

: '
This script will build and push your Docker image to 
Amazon ECR. Ensure aws cli is present on your 
machine, and you are authenticated before running this
script.

APP_IMAGE_NAME is an env variable that you must setup to
declare the name of the docker image

BUILD_TAG is an env variable that you can use to declare the
tag of the docker image

ECR_URL is an env variable that should be the complete 
ECR repository URL.

ECR_REPO is an env variable that should be the name of 
the ECR repository.
'

if [ -z "${APP_IMAGE_NAME}" ]; then
    echo "APP_IMAGE_NAME environment variable is required"
    exit 1
elif [ -z "${BUILD_TAG}" ]; then
    echo "BUILD_TAG environment variable is required"
    exit 1
elif [ -z "${ECR_URL}" ]; then
    echo "ECR_URL environment variable is required"
    exit 1
elif [ -z "${ECR_REPO}" ]; then
    echo "ECR_REPO environment variable is required"
    exit 1
fi

echo "Running docker build for production image"
docker build --platform=linux/amd64 -t "${APP_IMAGE_NAME}":"${BUILD_TAG}" .

if [ $? != 0 ]; then
    echo "There was an issue building the docker image, exiting"
    exit 1
fi

echo "Authenticating with ECR"
aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin ${ECR_URL}

if [ $? != 0 ]; then
    echo "Couldn't authenticate with ECR, exiting"
    exit 1
fi

echo "Tagging application ${BUILD_TAG} with ECR"
docker tag "${APP_IMAGE_NAME}":"${BUILD_TAG}" "${ECR_URL}"/"${ECR_REPO}":"${BUILD_TAG}"

if [ $? != 0 ]; then
    echo "Failed tagging the image with the target ECR repository, exiting"
    exit 1
fi

echo "Pushing the image to ECR"
docker push "${ECR_URL}"/"${ECR_REPO}":"${BUILD_TAG}"