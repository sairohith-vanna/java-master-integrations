#!/bin/sh

if [ -z "${ECS_CLUSTER}" ]; then
    echo "ECS_CLUSTER environment variable is required"
    exit 1
elif [ -z "${ECS_SERVICE}" ]; then
    echo "ECS_SERVICE environment variable is required"
    exit 1
fi

echo "Updating task definition with the latest build image"
envsubst < task-definition.json > task-definition-release.json

echo "Registering the latest task definition"
updateddefn=$(aws ecs register-task-definition --cli-input-json file://task-definition-release.json)

taskdefinitionarn=$(echo "$updateddefn" | jq -r '.taskDefinition.taskDefinitionArn')
echo "Latest task definition: $taskdefinitionarn"

# Update the service with latest task definition 
# using the extracted arn

echo "Updating the service ${ECS_SERVICE}"
updatedservice=$(aws ecs update-service \
    --cluster "${ECS_CLUSTER}" \
    --service "${ECS_SERVICE}" \
    --task-definition "$taskdefinitionarn" \
    --force-new-deployment)

if [ "${TRACK_DEPLOYMENT_STATUS}" = 1 ]; then
    sleep 5 # Waiting for the rollout to initiate, in case it takes time
    echo "Verifying rollout every 10 seconds for 10 minutes"
    totaldesired=$(echo "$updatedservice" | jq -r '.service.desiredCount')
    for _ in {1..60};
    do
        runningcount=$(aws ecs describe-services \
        --services jmi-sample-app-service-01 \
        --cluster java-mastery-sample-app-cluster \
        --query 'services[*].deployments[?status==`PRIMARY`].runningCount' | jq -r '.[0][0]')
        echo "Latest version running count: $runningcount"
        if [ "$runningcount" = "$totaldesired" ]; then
            echo "Deployment complete"
            exit 0
        fi
        echo "Deploying $((runningcount+1)) of $totaldesired containers"
        sleep 10
    done
    exit 1
fi
