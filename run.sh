#!/bin/sh  
parent_path=`pwd .`
echo $parent_path

java -cp $CLASSPATH:$parent_path/bin/lib/commons-logging-1.1.1.jar:$parent_path/bin/lib/jetty-util-7.1.14.jar:$parent_path/bin/lib/jetty-6.1.14.jar:$parent_path/bin/lib/common-logging-api-1.1.jar:$parent_path/bin/lib/servlet-api-2.5-6.1.14.jar:$parent_path/bin/lib/hadoop-hdfs-0.21.0.jar:$parent_path/bin/lib/hadoop-common-0.21.0.jar:/home/jiangbing/hadoop-0.21.0/conf/:$parent_path/bin/  -Djava.library.path=$parent_path/bin/lib $@

