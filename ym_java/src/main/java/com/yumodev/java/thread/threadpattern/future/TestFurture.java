package com.yumodev.java.thread.threadpattern.future;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestFurture {

    public static class Host{
        public Data request(final int count, final char c){
            System.out.println(" request("+count+","+ c+" ) begin");
            final FutureData futureData = new FutureData();
            new Thread(){
                @Override
                public void run() {
                   RealData realData = new RealData(count, c);
                   futureData.setRealData(realData);
                }
            }.start();


            System.out.println(" request("+count+","+ c+" ) end");

            return futureData;
        }
    }

    public interface Data{
        String getContent();
    }

    public static class FutureData implements Data{
        private RealData realData = null;
        private boolean ready = false;

        public synchronized void setRealData(RealData realData){
            if (ready){
                return;
            }

            this.realData = realData;
            this.ready = true;
            notifyAll();
        }
        @Override
        public synchronized String getContent() {
           while (!ready){
               try {
                   wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

           return realData.getContent();
        }
    }

    public static class RealData implements Data{
        private final String content;
        public RealData(int count, char c){

            System.out.println(" Real Data ("+count+","+ c+" ) begin");
            char[] buffer = new char[count];
            for (int i  =0; i < count; i++){
                buffer[i] = c;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            System.out.println(" Real Data ("+count+","+ c+" ) end");

            this.content = new String(buffer);
        }

        @Override
        public String getContent() {
            return content;
        }
    }



    public static void main(String[] args){
        System.out.println("main Begin");
        Host host = new Host();
        Data data1 = host.request(10, 'A');
        Data data2 = host.request(10, 'B');
        Data data3 = host.request(10, 'C');

        System.out.println("main otherJob Begin");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("mainOtherJob End");

        System.out.println("Data1 "+data1.getContent());
        System.out.println("Data2 "+data2.getContent());
        System.out.println("Data3 "+data3.getContent());
        System.out.println("main End");
    }
}
