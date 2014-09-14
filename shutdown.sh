export pid=`ps aux| grep Simple | awk 'NR==1{print $2}' | cut -d' ' -f1`;kill -9 $pid
echo "Simple server has been shutdown"
