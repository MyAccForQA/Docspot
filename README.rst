########################################
My Company Inc. [Selenium Tests]
########################################

This repository contains `Selenium <http://seleniumhq.org/>`_ tests for the `My Company Inc. <http://www.MyCompany.com/>`_, `GitHub Account <https://github.com/ACCOUNT/REPOSITORY>`_.


    .. image:: https://github.com/ACCOUNT/REPOSITORY/blob/master/screenshot/README/homepage.png
        :alt: My Company Inc.
        :width: 30%
        :align: center


.. contents::

.. section-numbering::

.. raw:: pdf

   PageBreak oneColumn


=============
Dependencies
=============
----------------
Git
----------------
Make sure you have `Git <https://git-scm.com/>`_ installed on your system. For check use the commands below:

``git -v``

----------------
GitHub
----------------
Make sure you have access `GitHub Account <https://github.com/ACCOUNT/REPOSITORY>`_:

``git clone https://github.com/ACCOUNT/REPOSITORY``

----------------
Maven
----------------
Make sure you have `Maven <https://maven.apache.org/download.cgi>`_ installed on your system. For check use the commands below:

``mvn -v/--version``

----------------
Java
----------------
Make sure you have `Java <http://www.java.com/>`_ installed on your system, if not follow the vendor instructions for installing them on your operating system.

``java -version``


=============
Run
=============
To run the framework using basic options and their syntax:

``mvn [clean] [test/site] [-DdriverType=FF] [-Ptest1/-Ptest2/-Ptest3] [-Dtestngfile=testng_xml_file]``

----------------
Clean
----------------
To clean the folders from test data from the previous run use the command [clean]

``mvn clean``

----------------
Run test without/with of report
----------------
To run the framework for execution you need to choose the desired option [test/site]

~~~~~~~~~~~~
Run test without of report
~~~~~~~~~~~~
To run tests without getting a report use option test

``mvn clean test``

~~~~~~~~~~~~
Run test with of report
~~~~~~~~~~~~
To run tests with getting a report use option site

``mvn clean site``


