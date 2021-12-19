I'm going to present a list of things that could be improved in a more professional context.

Coding
===

It'd make sense to separate unit and integration tests in different folders: src/test and src/it.

The Kafka configuration and error message handling could be improved.

There should be profiles for uat and prod. There we'd utilize a real forex client and restrict certain operations we allow in dev (creating tokens or sending messages to Kafka).

Authorization using roles (RBAC) would be probably needed.

Separation of OpenAPI operations in different functionalities (as of now all operations appear together).

Deployment
===

A pipeline (eg in GitLab) to automatize the docker image generation/ push to container registry should be implemented.

Volumes should be used so that the data in the pods survive restarts.

Credentials to be retrieved from secrets or a vault technology rather than appearing directly in the helm charts.

An ingress should be introduced (together with tls).

Some sensible settings should be applied to the number of replicas.

Use a proper namespace instead of the default one.
