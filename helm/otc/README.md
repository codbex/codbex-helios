## Chart installation steps

```shell
export GIT_REPO='<path-to-your-git-repo>'
export RELEASE_NAME='my-codbex-helios'
export KUBECONFIG='<path-to-your-kubeconfig>'

export CHART_FOLDER="$GIT_REPO/helm/otc"

export GIT_REPO='/Users/iliyan/work/git/codbex-helios'
export KUBECONFIG='/Users/iliyan/work/work-share/projects/open-telekom-cloud/marketplace/otc-deployment/kubeconfig-marketplace-app-testing.yaml'

# lint the chart
helm lint .

# Render chart templates locally and display the output.
helm template . --debug

# package the chart
cd "$CHART_FOLDER"
rm -f *.tgz
helm package .

# Uninstall previous release
helm uninstall $RELEASE_NAME --wait

# Default installation
helm install $RELEASE_NAME . --wait

###################################
# Other installation commands
###################################

## Install with disabled volumes
helm install $RELEASE_NAME . --set volume.enabled=false --wait

## Install with modified base path
helm install $RELEASE_NAME . --set ingress.path=/my-path --wait

## Install with disabled ingress
helm install $RELEASE_NAME . --set ingress.enabled=false --wait

## Install with LoadBalancer service
export LB_IP='80.158.91.18'
helm install $RELEASE_NAME . --set ingress.enabled=false --set service.type=LoadBalancer --set service.loadBalancerIp=$LB_IP --wait

## Install with existing ELB for ingress
export ELB_ID='646c69e0-a1d2-47ac-9ee4-cd9c0e011d50'
export ELB_TYPE='union'
helm install $RELEASE_NAME . --set ingress.elb.autocreate=false --set ingress.elb.existing.id=$ELB_ID --set ingress.elb.existing.class=$ELB_TYPE --wait

```
