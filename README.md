# Edison Service Discovery

Some services may have to figure out, which services are running in your system. There are a number of open source
discovery-services like Eureka or Consul. Because at otto.de, services are running in a Mesos infrastructure and 
managed using the Marathon framework, we do not need a dedicated discovery-server: the Marathon API contains all
information that is needed to discover running services.

[![Build Status](https://travis-ci.org/otto-de/edison-servicediscovery.svg)](https://travis-ci.org/otto-de/edison-servicediscovery) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.otto.edison/edison-servicediscovery/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.otto.edison/edison-servicediscovery)


## Environment Properties

Enable Marathon service discovery by providing the following properties:

* edison.servicediscovery.marathon.url: The URL of the marathon server (http://marathon.example.org)
* edison.servicediscovery.marathon.username: Optional user used for authentication at the marathon server
* edison.servicediscovery.marathon.password: Optional password used for authentication at the marathon server

Configure the generation of service URIs:

* edison.servicediscovery.serviceUriTemplate=http://{env}.example.org/{group}-{service},live=https://www.example.org/{group}-{service}
* edison.servicediscovery.internalUriTemplate=http://{service}.{group}.{env}.example.org/{group}-{service}

URI templates may contain the following placeholders:

* service: the name of the service
* group: the group of the service (several service may belong to a group, like, for example, a team or bounded context.
* env: the staging environment of the deployed service. Something like "ci", "prelive" or "live".

Environment-specific templates can be configured by configuring a comma-separated list of URI templates, where the
first entry is the default template, the following templates are <env>=<template pairs: <default-template>,<env>=<env-specific-template>, ...

Example:
edison.servicediscovery.serviceUriTemplate=http://{env}.example.org/{group}-{service},live=https://www.example.org/{group}-{service}

These patterns will generate something like https://www.example.org/order-shoppingcart in the live stage, or
http://prelive.example.org/order-shoppingcart in a prelive stage.

## Conditional Spring Beans

If edison.servicediscovery.marathon.url is provided, the MarathonDiscoveryService is auto-configured. 

Information like environment, group and service name is extracted from the Marathon appId using an AppIdParser.
By default, the @ConditionalOnMissingBean DefaultAppIdParser is used. 

The strategy to generate service URLs is DefaultServiceUrlFactory. You may override this implementation by your own
ServiceUrlFactory by configuring a Spring Bean of this type.