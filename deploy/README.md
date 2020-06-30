# Smart Commute Deployment

A `docker-compose.yml` file is provided to demonstrate what are the services involved. Starting from this `yml` file, it is possible to generate the Openshift resources using [kompose](https://kompose.io/).

Before using `docker-compose`, it is needed to run the [pipeline to build the services](pipelines/build-docker-images.sh) and set the following enviroment variables

```bash
export BOOTSTRAP_SERVERS=kafka-cluster:9093
export DATA_LAKE_API_KEY=<REDACTED>
export DATA_LAKE_SERVICE_INSTANCE_ID=<REDACTED>
export HERE_API_KEY=<REDACTED>
export HERE_APP_KEY=<REDACTED>
```

Ensure to replace the strings `<REDACTED>` with your keys. You can gen an HERE account for free [here](https://developer.here.com/projects) and then get the `Location Services` api keys.

Note that also a `Cloud Object Storage` is needed: you can get a free IBM cloud account [here](https://cloud.ibm.com/). 
