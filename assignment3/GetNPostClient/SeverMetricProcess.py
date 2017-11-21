#!/usr/bin/python
# -*- coding: utf-8 -*-

import psycopg2
import sys

con = None
HOST = 'postgresql.cbouijouwxsf.us-west-2.rds.amazonaws.com'
PORT = '5432'
DBNAME = 'mydevdb'
USER = 'uuuu'
PASSWORD = '12345678'
CONNECT = "host='{host}' port='{port}' dbname='{dbname}' user='{user}' password='{password}'"

try:
    con = psycopg2.connect(CONNECT.format(host=HOST, port=PORT, dbname=DBNAME, user=USER, password=PASSWORD))
    cur = con.cursor()

    cur.execute("select count(*) from server_table where flag=0")
    post_request_num = cur.fetchone()
    print("total number of post is:", post_request_num[0])

    cur.execute("select count(*) from server_table where flag=1")
    get_request_num = cur.fetchone()
    print("total number of get is:", get_request_num[0])

    cur.execute("select count(errorNum) from server_table")
    error_num = cur.fetchone();
    print("total number of error is:", error_num[0])

    cur.execute("select avg(responseTime) from server_table where flag=0")
    mean_post = cur.fetchone();
    print("mean response time of post is: ", mean_post[0])

    cur.execute("select avg(responseTime) from server_table where flag=1")
    mean_get = cur.fetchone();
    print("mean response time of get is: ", mean_get[0])

    cur.execute("select avg(responseTime) from server_table")
    mean_overall = cur.fetchone();
    print("mean response time of overall is: ", mean_overall[0])

    cur.execute("select percentile_cont(0.5) within group (order by responseTime) from server_table where flag=0")
    median_post = cur.fetchone()
    print("median response time of post is:", median_post[0])

    cur.execute("select percentile_cont(0.5) within group (order by responseTime) from server_table where flag=1")
    median_get = cur.fetchone()
    print("median response time of get is:", median_get[0])

    cur.execute("select percentile_cont(0.5) within group (order by responseTime) from server_table")
    median_overall = cur.fetchone()
    print("median response time of overall is:", median_overall[0])

    cur.execute("select percentile_cont(0.95) within group (order by responseTime) from server_table where flag=0")
    post_95 = cur.fetchone()
    print("95th percentile response time of post is:", post_95[0])

    cur.execute("select percentile_cont(0.95) within group (order by responseTime) from server_table where flag=1")
    get_95 = cur.fetchone()
    print("95th percentile response time of get is:", get_95[0])

    cur.execute("select percentile_cont(0.95) within group (order by responseTime) from server_table")
    overall_95 = cur.fetchone()
    print("99th percentile response time of overall is:", overall_95[0])

    cur.execute("select percentile_cont(0.99) within group (order by responseTime) from server_table where flag=0")
    post_99 = cur.fetchone()
    print("99th percentile response time of post is:", post_99[0])

    cur.execute("select percentile_cont(0.99) within group (order by responseTime) from server_table where flag=1")
    get_99 = cur.fetchone()
    print("99th percentile response time of get is:", get_99[0])

    cur.execute("select percentile_cont(0.99) within group (order by responseTime) from server_table")
    overall_99 = cur.fetchone()
    print("99th percentile response time of overall is:", overall_99[0])

    cur.execute("select count(*) from server_table")
    total_request_num = cur.fetchone()
    if total_request_num[0] == 60000:
        cur.execute("delete from server_table")

except (Exception, psycopg2.DatabaseError) as error:
        print(error)

finally:
    if con:
        con.close()