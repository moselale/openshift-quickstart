#!/bin/bash
 
### Openshift deploy script

if [ $1 ]; then

  echo "Creazione pacchetto versione " $1
    mvn clean package -DskipTests=true
    if [ $? != 0 ]; then
        echo "building error"
        return
    fi

  echo "Esegucuzione build con Podman"
    podman build -f src/main/docker/Dockerfile.jvm -t quarkus/openshift-quickstart-jvm .
    if [ $? != 0 ]; then
        echo "podman build error"
        return
    fi

  echo "Tag locale"
    podman tag quarkus/openshift-quickstart-jvm default-route-openshift-image-registry.apps.poc-oc.poc.oc/quarkus/openshift-quickstart-jvm:$1
    if [ $? != 0 ]; then
        echo "podman tag error"
        return
    fi

  echo "Podman push"
    podman push default-route-openshift-image-registry.apps.poc-oc.poc.oc/quarkus/openshift-quickstart-jvm:$1 --tls-verify=false
    if [ $? != 0 ]; then
        echo "podman push error trying to login and retry"
        podman login -u admin -p $(oc whoami -t) default-route-openshift-image-registry.apps.poc-oc.poc.oc --tls-verify=false
        podman push default-route-openshift-image-registry.apps.poc-oc.poc.oc/quarkus/openshift-quickstart-jvm:$1 --tls-verify=false
        if [$? != 0 ]; then
            podman push default-route-openshift-image-registry.apps.poc-oc.poc.oc/quarkus/openshift-quickstart-jvm:$1 --tls-verify=false
            if [$? != 0 ]; then
                echo "Podman push error, prova ad autenticarti su Openshift usando 'oc login'"
                return
            fi
        fi
    fi

  echo "Tag su Openshift per permettere l'aggiornamento automatico dei Pod"
    oc tag quarkus/openshift-quickstart-jvm:$1 quarkus/openshift-quickstart-jvm:latest
    if [ $? != 0 ]; then
        echo "oc tag error"
        return
    fi

else
  echo "Inserisci la versione come parametro"
fi