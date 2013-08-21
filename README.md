# CoSign

CoSign helps develop templates for sustainability reporting.


## Installation

To install CoSign, you need, as a minimum:

1. JDK 7+
2. Tomcat 7
3. Postgres 9.0


## Setup a VPS (Detailed instructions)

Assuming a clean VPS, these instructions talk through the full steps to install CoSign.

**1. Configure a non-root user**

Add a user:

    useradd newuser
    passwd newuser

Then grant the newuser access to sudo, and switch to the user:

    usermod -a -G sudo newuser
    su newuser

**2. Install JDK 7+**

This can be done by installing from apt-get:

    sudo apt-get install python-software-properties
    sudo add-apt-repository ppa:webupd8team/java
    sudo apt-get update
    sudo apt-get install oracle-java7-installer

Or you can download and install directly from Oracle (make sure the correct platform is included for the first command):

    wget --no-cookies --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com" "http://download.oracle.com/otn-pub/java/jdk/7/jdk-7-linux-i586.tar.gz"
    tar -zxvf jdk-7-linux-i586.tar.gz
    sudo mkdir -p /usr/lib/jvm/jdk1.7.0
    sudo mv jdk1.7.0_03/* /usr/lib/jvm/jdk1.7.0/
    sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
    sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
    sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1

**3. Now install Tomcat 7**

Download from:

    wget http://mirror.mel.bkb.net.au/pub/apache/tomcat/tomcat-7/v7.0.34/bin/apache-tomcat-7.0.34.tar.gz
    tar -zxvf apache-tomcat-7.0.34.tar.gz

To test Tomcat, then:

    cd apache-tomcat-7.0.34/bin
    ./startup.sh

Visiting http://[VPS IP address]:8080 should show the default Tomcat page.

**4. Install Postgres**

To install from source, download from the Postgres website, e.g.

    wget ftp://ftp.postgresql.org/pub/source/v9.2.2/postgresql-9.2.2.tar.gz
    tar -zxvf postgresql-9.2.2.tar.gz
    cd postgresql-9.2.2

Then install some dependencies:

    sudo apt-get install libreadline-dev

Then install Postgres, as per the [Postgres documentation](http://www.postgresql.org/docs/current/static/install-procedure.html)

    cd postgresql-9.2.2
    sudo ./configure
    sudo make
    sudo make install

Next, setup a postgres user [following instructions here](http://autofei.wordpress.com/2011/02/17/install-postgresql-9-0-from-source-code-at-ubuntu/):

    sudo mkdir /usr/local/pgsql/data
    sudo adduser postgres
    sudo chown postgres /usr/local/pgsql/data
    su - postgres
    /usr/local/pgsql/bin/initdb -D /usr/local/pgsql/data/
    cd /usr/local/pgsql/data/
    mkdir log
    /usr/local/pgsql/bin/postgres -D /usr/local/pgsql/data >/usr/local/pgsql/data/log/logfile 2>&1 &

Finally create the database:

    cd ..
    bin/createdb mydb
    bin/psql mydb




## Configuring SDB

SDB (http://jena.apache.org/documentation/sdb/index.html) is a component, part of the Jena framework,
for storing and retrieving RDF in a relational database. For CoSign, the underlying database is Postgres.

The source for CoSign includes the full distribution of SDB (1.3.4), under sdb-dist/. To setup SDB, do the following:

1. First create a Postgres database called 'mydb'. This can be done with:

  createdb mydb

2. Open a terminal (needs to be Cygwin on Windows), and cd to the sdb-dist/bin directory.

3. From the sdb-dist/bin directory, configure the required SDB parameters:

  export SDBROOT=..
  export SDB_JDBC=../../lib/postgresql-9.0-801.jdbc4.jar
  export SDB_USER=[your Postgres username]
  export SDB_PASS=[your Postgres password]

4. Set the classpath:

  ./make_classpath ../lib

5. Set up the SDB database with:

  ./sdbconfig --sdb=../../war/sdb.ttl --create

Other SDB options and utilities can be found at:

  http://jena.apache.org/documentation/sdb/installation.html



## Setting up CoSign

Once SDB is installed, CoSign


## Code Structure








