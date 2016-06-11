=====================
Fibonacci Service
=====================

This is a Fibonacci REST API that returns fibonacci series of given number

Quick Guide
===========

Running on Mesos Cluster
------------------------

To run this service on mesos cluster, post ``marathon.json`` file in repository to the marathon framework.

Use ``curl`` to submit the file to Marathon::

    curl -sXPOST "http://master.mesos:8080/v2/apps" -d@marathon.json -H "Content-Type: application/json" | jq .
    
Build from source
-----------------

However, the ``jar`` file can also be built from source::

    ./gradlew fatJar

The output jar file can be found in ``build/libs/`` and need to be hosted on
your own.

Running service in Docker container
-----------------------------------

If you want to run this service in Docker container, I already pushed the image to docker hub. available at aslanbekirov/fibonacci-service.  
All you have to do is to run command below::

    docker run -p 5555:5555 -d aslanbekirov/fibonacci-service 

If you want to build your own image, I have provided ``Dockerfile`` . Use command below to build your own image, but before that you must follow steps described in ``Build from source``
command::

    docker build -t imageName

API Usage
=========
If your service is up and running, now lets go through its usage. This is a rest api where you can use ``GET`` method to get fibonacci series of particular number and URI pattern is as follows::

    http://hostIP:5555/fibonacci/{type}/{number}

Since fibonacci series of a number can be calculated in recursive and nonrecursive manner, you can provide this as a type. If you want calculation to be done in recursive manner, provide ``recursive`` as a type and ``nonrecursive`` otherwise. In addition, obviously ``{number}`` is the number parameter that you want fibonacci series calculated. See some examples below::

   curl http://10.20.30.40:5555/fibonacci/recursive/10
   curl http://10.20.30.40:5555/fibonacci/nonrecursive/15        

Example result will look like below:

.. code-block:: json

    {
      "status": 200,
      "message": {
        "result": [
          0,
          1,
          1,
          2
        ],
        "calculationType": "nonrecursive"
      }
    }

Enjoy your Fibonacci series :) 
    
