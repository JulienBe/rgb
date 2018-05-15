#!/bin/bash

for i in {1..10}
do
	gradle run &
done
sleep 5
pkill -f rgb
