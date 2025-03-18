# Post-deployment guide
The following documentation describes what post-deployment configurations need to be applied depending on the configuration you used during the installation.

<!-- TOC -->
* [Post-deployment guide](#post-deployment-guide)
  * [Installation options](#installation-options)
    * [Configured TLS (HTTPS protocol)](#configured-tls-https-protocol)
      * [CCE ingress with autocreated ELB](#cce-ingress-with-autocreated-elb)
      * [CCE ingress with existing ELB](#cce-ingress-with-existing-elb)
      * [NGINX ingress and user provided certificate](#nginx-ingress-and-user-provided-certificate)
      * [NGINX ingress and cert manager generated certificate](#nginx-ingress-and-cert-manager-generated-certificate)
    * [Disabled TLS (HTTP protocol)](#disabled-tls-http-protocol)
      * [CCE ingress and autocreate ELB (default installation)](#cce-ingress-and-autocreate-elb-default-installation)
      * [Existing CCE ELB for ingress](#existing-cce-elb-for-ingress)
      * [NGINX ingress](#nginx-ingress)
  * [Login](#login)
<!-- TOC -->

## Installation options

### Configured TLS (HTTPS protocol)

__Prerequisites__
- Configured DNS in OTC console for your domain - for example for domain `eu3.codbex.com`
- Record for a subdomain (for example `rhea-demo`) which directs to the need ELB IP

#### CCE ingress with autocreated ELB
- Once the installation is completed, update your DNS record to use the generated ELB IP address.

- Access the application at the registered domain.<br>
  For example [https://rhea-demo.eu3.codbex.com](https://rhea-demo.eu3.codbex.com).

#### CCE ingress with existing ELB
- Once the installation is completed, update your DNS record to use the existing ELB IP address.
- Access the application at the registered domain.<br>
  For example [https://rhea-demo.eu3.codbex.com](https://rhea-demo.eu3.codbex.com).

#### NGINX ingress and user provided certificate
- Once the installation is completed, update your DNS record to use the NGINX ELB IP address.
- Access the application at the registered domain.<br>
  For example [https://rhea-demo.eu3.codbex.com](https://rhea-demo.eu3.codbex.com).

#### NGINX ingress and cert manager generated certificate
- Once the installation is completed, update your DNS record to use the NGINX ELB IP address.
- Access the application at the registered domain.<br>
  For example [https://rhea-demo.eu3.codbex.com](https://rhea-demo.eu3.codbex.com).

---

### Disabled TLS (HTTP protocol)

#### CCE ingress and autocreate ELB (default installation)
Execute the following script to get the application URL.
The example uses namespace `marketplace-testing`
```shell
export ELB_IP=$(kubectl get ingress codbex-rhea-ingress -o jsonpath='{.metadata.annotations.kubernetes\.io/elb\.ip}' -n marketplace-testing)
echo "Application URL: http://$ELB_IP:80"
```

#### Existing CCE ELB for ingress
To access the application, open URL `http://<elb-ip>:80` where <elb-ip> is the IP address of used existing OTC Elastic Load Balancer.

#### NGINX ingress
Execute the following script to get the application URL.
The example uses namespace `marketplace-testing`
```shell
export ELB_IP=$(kubectl get ingress codbex-rhea-ingress -o jsonpath='{.status.loadBalancer.ingress[1].ip}' -n marketplace-testing)
echo "Application URL: http://$ELB_IP:80"
```

## Login
Default credentials are username `admin` and password `admin`, unless you have specified custom credentials.
