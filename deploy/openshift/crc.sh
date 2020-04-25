./crc config set memory 16384
./crc config set cpus 5
./crc start
eval $(./crc oc-env)
# login with credentials

oc new-project smartcommute-2020
oc adm policy add-scc-to-user anyuid -n smartcommute-2020 -z default
oc create -f .
