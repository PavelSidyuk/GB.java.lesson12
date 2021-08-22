import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    public static void main (String[] args) {

        System.out.println(Thread.currentThread());
        doWork(arr);

        for (int i = 0; i < 2; i++) {
            new DoWorkV2("поток " + (i + 1)).start();

        }
        new DoWorkV3("способ 2").start();

    }


    public static void doWork (float[] f) {

        long a = System.currentTimeMillis();

        for (int i = 0; i < f.length; i++) {
            f[i] = 1;

            // System.out.println(System.currentTimeMillis()-a);
        }

        for (int i = 0; i < f.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        }
        System.out.println(System.currentTimeMillis() - a);

    }


    public static class DoWorkV2 extends Thread {


        public DoWorkV2 (String name) {
            super(name);

        }


        @Override
        public void run () {
            long a = System.currentTimeMillis();
            System.out.println(Thread.currentThread());


            for (int i = 0; i < arr.length; i++) {
                arr[i] = 1;

            }

            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                //System.out.println(arr[i] + " " + Thread.currentThread());//  проверяю что метод работает в 2х потоках
                // но по времени выполняется дольше чем в одном потоке. Не понимаю почему.
                // точнее мне кажется что потоки не разбивают задачу поровну, а делают 2 раза одно и тоже


            }


            System.out.println(System.currentTimeMillis() - a);
        }

    }

    public static class DoWorkV3 extends Thread {

        long a = System.currentTimeMillis();



        public DoWorkV3 (String name) { // короче тоже долго.
            super(name);
        }

        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];

        @Override
        public void run () {
            System.out.println(Thread.currentThread());
            for (int i = 0; i < arr.length; i++) { // сначала инициализирую массив
                arr[i] = 1;

            }

            System.arraycopy(arr, 0, arr1, 0, arr1.length);       // разбиваю массив на 2
            System.arraycopy(arr, arr1.length, arr2, 0, arr2.length);

            for (int i = 0; i < arr1.length; i++) {  //произвожу действия с первой частью массива
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            for (int i = 0; i < arr2.length; i++) {//произвожу действия со второй частью массива
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(arr1, 0, arr, 0, arr1.length);
            System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);// произвожу склейку

            System.out.println(System.currentTimeMillis() - a);
        }
    }

}
