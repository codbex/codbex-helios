## Chart installation steps

```shell
export KUBECONFIG='<path-to-your-kubeconfig>'
export GIT_REPO='<path-to-your-git-repo>'
export RELEASE_NAME='my-codbex-helios'
export NAMESPACE='default'

export CHART_FOLDER="$GIT_REPO/helm/otc"

# lint the chart
helm lint .

# Render chart templates locally and display the output.
helm template . --debug

# package the chart
cd "$CHART_FOLDER"
rm -f *.tgz
helm package .

# Uninstall previous release
helm uninstall $RELEASE_NAME --wait --namespace $NAMESPACE

# Default installation
helm install $RELEASE_NAME . --wait --namespace $NAMESPACE --create-namespace 

# Install from OCI
helm install $RELEASE_NAME oci://ghcr.io/codbex/helm/codbex-helios-1.24.0-otc/codbex-helios --version 1.24.0 --atomic --timeout 2m0s

# Upgrade
helm upgrade $RELEASE_NAME . --install --atomic \
  --wait --namespace $NAMESPACE --create-namespace 

# Upgrade from URL
CHART_URL='https://github.com/codbex/codbex-helios/releases/download/v1.20.0/codbex-helios-1.20.0-otc.tgz'
helm upgrade $RELEASE_NAME "$CHART_URL" --install --atomic \
  --wait --namespace $NAMESPACE --create-namespace 

###################################
# Other installation commands
###################################

## Install with disabled volumes
helm install $RELEASE_NAME . --set volume.enabled=false \
  --wait --namespace $NAMESPACE

## Install with modified base path
helm install $RELEASE_NAME . --set ingress.path=/my-path \
  --wait --namespace $NAMESPACE

## Install with disabled ingress
helm install $RELEASE_NAME . --set ingress.enabled=false \
  --wait --namespace $NAMESPACE

## Install with LoadBalancer service
export LB_IP='80.158.91.18'
helm install $RELEASE_NAME . --set ingress.enabled=false --set service.type=LoadBalancer \
  --set service.loadBalancerIp=$LB_IP --wait --namespace $NAMESPACE

## Install with existing ELB for ingress
export ELB_ID='646c69e0-a1d2-47ac-9ee4-cd9c0e011d50'
export ELB_TYPE='union'
helm install $RELEASE_NAME . --set ingress.elb.autocreate=false \
  --set ingress.elb.existing.id=$ELB_ID --set ingress.elb.existing.class=$ELB_TYPE \
  --wait --namespace $NAMESPACE

```
