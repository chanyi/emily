package com.emily.emilyservice;

import java.util.concurrent.RecursiveTask;

class MyForkJoinTask extends RecursiveTask<Long> {
    private long start;
    private long end;

    private long temp = 10000;

    public MyForkJoinTask(long start,long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        if((end-start)<temp){
            for (long i = start; i < end; i++) {
                sum++;
            }
            return sum;
        }else{
            long mid = start + (end-start)/2;
            MyForkJoinTask myForkJoinTask1 = new MyForkJoinTask(start,mid);
            myForkJoinTask1.fork();
            MyForkJoinTask myForkJoinTask2 = new MyForkJoinTask(mid+1,end);
            myForkJoinTask2.fork();
            return myForkJoinTask1.join()+ myForkJoinTask2.join();
        }
    }

}