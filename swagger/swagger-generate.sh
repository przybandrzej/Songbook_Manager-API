java -jar swagger-codegen-cli.jar generate \
  -i http://localhost:3000/v2/api-docs \
  --api-package com.lazydev.stksongbook.mailer.client.api \
  --model-package com.lazydev.stksongbook.mailer.client.model \
  --invoker-package com.lazydev.stksongbook.mailer.client.invoker \
  --group-id com.lazydev \
  --artifact-id mailer.client \
  --artifact-version 0.0.1-SNAPSHOT \
  -l java \
  --library resttemplate \
  -o ../

rm -rf ../gradle
rm -rf ../.swagger-codegen
