package cn.ict.binos.test.performance;

public class testLocalReadMem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] data = new byte[TestUtility.size];
		byte tmp;
		long delta = System.nanoTime();
		for (int i = 0; i < TestUtility.readCount; i++) {
			for (int j =0; j < TestUtility.size; j++) {
				
			}
		}
		delta = System.nanoTime() - delta;
		long start = System.nanoTime();
		for (int i = 0; i < TestUtility.readCount; i++) {
			for (int j =0; j < TestUtility.size; j++) {
				tmp = data[j];
			}
		}
		System.out.println("local mem used Time:" + (System.nanoTime() - start));
	}

}
