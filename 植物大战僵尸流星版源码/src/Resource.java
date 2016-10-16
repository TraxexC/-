import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.loon.framework.javase.game.action.sprite.Animation;
import org.loon.framework.javase.game.action.sprite.GIFAnimation;
import org.loon.framework.javase.game.core.graphics.LFont;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;

public class Resource {
	private static final LImage backgroundpic;
	private static final LImage maingroundpic;// ��½ҳ����ͼƬ
	private static final LImage star; // ��Ƭ����ͼƬ
	private static final LImage upStar; // ��Ƭ������ͼƬ
	private static final LImage scoopFrame; // ��Ƭ������ͼƬ
	private static final LImage actionFlag;
	private static final LImage scoop1;
	private static final LImage chooseImage; 
	private static final LImage quit1;
	private static final LImage quit2;
	private static final LImage lost = new LImage("image/lost.png");
	private static final LImage win = new LImage("image/win.png");
	private static final LImage backgroundpic1;
	private static final LImage shadow; // ��ӰͼƬ
	private static final LImage stagePic = new LImage("image/stagePic.png");
	private static final LImage timePic = new LImage("image/timePic.png");
	private static String dropImage = "image/drop.gif"; // ̫��ͼƬ
	private static int bulletAnimationNum = 39;
	private static int plantAnimationNum = 81;
	private static int zombieAnimationNum = 5;
	private static int magicAnimationNum = 11;
	private static int cardNum = 82;
	private static Animation[] bulletAnimation = new Animation[bulletAnimationNum];
	private static Animation[] plantAnimation = new Animation[plantAnimationNum];
	private static Animation[] zombieAnimation = new Animation[zombieAnimationNum];
	private static Animation[] magicAnimation = new Animation[magicAnimationNum];
	private static LImage[] allCardImage = new LImage[cardNum];
	private static String[] cardInfomation = new String[400];
	private static List<int[]> cardInfo = new ArrayList<int[]>();
	private static List<int[]> plantInfo = new ArrayList<int[]>();
	private static List<int[]> zombieInfo = new ArrayList<int[]>();
	private static final int cardIntNum = 26; // ÿ����Ƭ��Ϣ����Ĵ�С
	private static final int plantIntNum = 50; // ÿ��ֲ����Ϣ����Ĵ�С
	private static final int zombieIntNum = 58; // ÿ����ʬ��Ϣ����Ĵ�С

	public static String getDropImage() {
		return dropImage;
	}

	static {
		initCardInfomation();
		star = new LImage("image/star.png");
		upStar = new LImage("image/upstar.gif");
		scoopFrame = new LImage("image/scoopframe.png");
		scoop1 = new LImage("image/scoop1.png");
		shadow = new LImage("image/shadow.png");
		backgroundpic1 = new LImage("image/background2.jpg");
		quit1 = new LImage("image/quit1.png");
		quit2 = new LImage("image/quit2.png");
		maingroundpic = new LImage("image/mainbackground.jpg");
		backgroundpic = new LImage("image/background.jpg");
		actionFlag = new LImage("image/actionFlag.png");
		chooseImage = new LImage("image/chooseImage.jpg");
		for (int i = 0; i < magicAnimationNum; i++) {
			if (i == 4 || i == 5 || i == 6 || i == 7) {
				magicAnimation[i] = Animation.getDefaultAnimation(
						"image\\magic" + (i + 1) + ".png", 136, 95, 10);

			} else {
				magicAnimation[i] = new GIFAnimation("image/magic" + (i + 1)
						+ ".gif").getAnimation();
			}

		}
	}

	public static LImage getCardImage(int cardID) {
		if (null == Resource.allCardImage[cardID - 1]) {
			Resource.allCardImage[cardID - 1] = new LImage("image/card"
					+ cardID + ".png");
		}
		return Resource.allCardImage[cardID - 1];

	}

	private static Animation arrowAnimation = Animation.getDefaultAnimation(
			"image\\arrow.png", 19, 80, 10);

	public static Animation getArrowAnimation() {
		return arrowAnimation;
	}

	public static LImage getShadow() {
		return shadow;
	}

	Resource() {
	}



	public static LImage getActionFlag() {
		return actionFlag;
	}

	public static LImage getQuit1() {
		return quit1;
	}
	
	public static LImage getQuit2() {
		return quit2;
	}
	
	public static LImage getStagePic() {
		return stagePic;
	}

	public static LImage getTimePic() {
		return timePic;
	}

	public static String[] getCardInfomation() {
		return cardInfomation;
	}

	public static LImage getChooseImage() {
		return chooseImage;
	}

	public static LImage getBackGroundPic() {
		return backgroundpic;
	}
	
	public static LImage getScoopFrame() {
		return scoopFrame;
	}

	public static LImage getStar() {
		return star;
	}

	public static LImage getMaingroundpic() {
		return maingroundpic;
	}

	public static LImage getScoop1() {
		return scoop1;
	}

	public static LImage getMixCardFrame() {
		return backgroundpic1;
	}

	public static LImage getUpStar() {
		return upStar;
	}

	public static int[] getCardInfo(int cardID) {
		int[] i = Resource.cardInfo.get(cardID - 1);
		return i;
	}

	public static Animation getPlantAnimation(int plantID) {
		if (null == plantAnimation[plantID - 1]) {
			plantAnimation[plantID - 1] = new GIFAnimation("image/plant"
					+ plantID + ".gif").getAnimation();
		}
		return (Animation) plantAnimation[plantID - 1].clone();
	}

	public static Animation getBulletAnimation(int bulletID) {
		if (null == bulletAnimation[bulletID - 1]) {
			bulletAnimation[bulletID - 1] = new GIFAnimation("image/b"
					+ bulletID + ".gif").getAnimation();
		}
		return (Animation) bulletAnimation[bulletID - 1].clone();
	}

	public static Animation getZombieAnimation(int zombieID) {
		if (null == zombieAnimation[zombieID - 1]) {
			zombieAnimation[zombieID - 1] = new GIFAnimation("image/zombie"
					+ zombieID + ".gif").getAnimation();
		}
		return (Animation) zombieAnimation[zombieID - 1].clone();
	}

	public static Animation getMagicAnimation(int magicID) {
		return (Animation) magicAnimation[magicID - 1].clone();

	}

	public static int[] getPlantInfo(int plantID) {
		return Resource.plantInfo.get(plantID - 1);
	}

	public static int[] getZombieInfo(int zombieID) {
		return Resource.zombieInfo.get(zombieID - 1);
	}

	public static LImage getLost() {
		return lost;
	}

	public static LImage getWin() {
		return win;
	}

	public static void readInfo() {
		// ����Ƭ��Ϣ
		BufferedReader reader = null;
		int[] info = new int[cardIntNum];
		int i = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(Resource.class
					.getResourceAsStream("config/cardInfo.txt")));
			String line = reader.readLine(); // ����һ��
			while (line != null) { // ���û���꣬����
				// ��","���
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {

					int x = Integer.parseInt(st.nextToken());
					info[i] = x;
					i++;
					// System.out.println("i:"+i+",x:"+x);

				}
				Resource.cardInfo.add(info.clone());
				i = 0;
				line = reader.readLine(); // ������һ��
			}
		} catch (java.io.IOException ioe) {
			// ������
		} finally { // �ر��ļ�

			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ����ֲ����Ϣ
		reader = null;
		info = new int[plantIntNum];
		i = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(Resource.class
					.getResourceAsStream("config/plantInfo.txt")));
			String line = reader.readLine(); // ����һ��
			while (line != null) { // ���û���꣬����
				// ��","���
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {

					int x = Integer.parseInt(st.nextToken());
					info[i] = x;
					i++;

				}
				Resource.plantInfo.add(info.clone());
				i = 0;
				line = reader.readLine(); // ������һ��
			}
		} catch (java.io.IOException ioe) {
			// ������
		} finally { // �ر��ļ�

			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ����ʬ��Ϣ

		reader = null;
		info = new int[zombieIntNum];
		i = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(Resource.class
					.getResourceAsStream("config/zombieInfo.txt")));
			String line = reader.readLine(); // ����һ��
			while (line != null) { // ���û���꣬����
				// ��","���
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					int x = Integer.parseInt(st.nextToken());
					info[i] = x;
					i++;

				}
				Resource.zombieInfo.add(info.clone());
				i = 0;
				line = reader.readLine(); // ������һ��
			}
		} catch (java.io.IOException ioe) {
			// ������
		} finally { // �ر��ļ�

			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static List<int[]> getCardInfo() {
		return cardInfo;
	}

	/**
	 * @���� ��������Ĵ���
	 * @�����б�: srcString String
	 * @return String ����һ��String����
	 */
	public static String getChineseString(String srcString) {
		byte[] b = new byte[srcString.length()];
		try {
			b = srcString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return (new String(b));
	}

	/**
	 * ��Դ�ַ����Զ���ָ����Ȼ�����ʾ
	 * 
	 * @param g
	 *            ����
	 * @param sourceString
	 *            Դ�ַ���
	 * @param viewX
	 *            ��ʾ��x����
	 * @param viewY
	 *            ��ʾ��y����
	 * @param width
	 *            ָ������ʾ���
	 * @param font
	 *            ����
	 * @param color
	 *            ������ɫ
	 */
	public static void cutString(LGraphics g, String sourceString, int viewX,
			int viewY, int width, LFont font, Color c) {
		int totalNum = sourceString.length();// �ַ�����
		int preWorldMaxWidth = font.charWidth('��');// �����ַ������

		int worldHeight = font.getHeight();// �ַ��ĸ߶�

		int preLineMinNum = width / preWorldMaxWidth;// ÿ�������ַ���

		int remanentNum = totalNum;// ʣ����ַ���

		int curLineNum;// ��ǰ��������ʾ���ַ���

		int row = 0;// ��ʾ������
		g.setColor(c);
		while (true) {
			if (remanentNum <= 0)
				break;
			if (remanentNum >= preLineMinNum) {
				curLineNum = preLineMinNum;
				while (font.subStringWidth(sourceString,
						totalNum - remanentNum, curLineNum) <= width
						- preWorldMaxWidth) {
					if (curLineNum >= remanentNum)
						break;
					curLineNum++;
				}
				g.drawSubString(sourceString, totalNum - remanentNum,
						curLineNum, viewX, viewY + (row * worldHeight), 0);
				remanentNum -= curLineNum;
				row++;
			} else {
				g.drawSubString(sourceString, totalNum - remanentNum,
						remanentNum, viewX, viewY + (row * worldHeight), 0);
				remanentNum = 0;
			}
		}
	}

	// ��ʼ����Ƭ��Ϣ����
	public static void initCardInfomation() {
		cardInfomation[0] = "һ�������淢չ���Ƚ�ƽ����ֲ��Ƚ��ʺ�ǰ��ʹ�ã��������������ܵõ�����";
		cardInfomation[1] = "һ�������淢չ���Ƚ�ƽ����ֲ��Ƚ��ʺ�ǰ��ʹ�ã��������������ܵõ�����";
		cardInfomation[2] = "һ�������淢չ���Ƚ�ƽ����ֲ��Ƚ��ʺ�ǰ��ʹ�ã��������������ܵõ�����";
		cardInfomation[3] = "һ�������淢չ���Ƚ�ƽ����ֲ��Ƚ��ʺ�ǰ��ʹ�ã��������������ܵõ�����";
		cardInfomation[4] = "һ�������淢չ���Ƚ�ƽ����ֲ��Ƚ��ʺ�ǰ��ʹ�ã��������������ܵõ�����";
		cardInfomation[5] = "ӵ�нϸ߷��������Ĺ�����ֲ����ǹ������ϵͣ��ǳ��ʺ�ǰ��ʹ�ã���������������õ���һ������";
		cardInfomation[6] = "ӵ�нϸ߷��������Ĺ�����ֲ����ǹ������ϵͣ��ǳ��ʺ�ǰ��ʹ�ã���������������õ���һ������";
		cardInfomation[7] = "ӵ�нϸ߷��������Ĺ�����ֲ����ǹ������ϵͣ��ǳ��ʺ�ǰ��ʹ�ã���������������õ���һ������";
		cardInfomation[8] = "ӵ�нϸ߷��������Ĺ�����ֲ����ǹ������ϵͣ��ǳ��ʺ�ǰ��ʹ�ã���������������õ���һ������";
		cardInfomation[9] = "ӵ�нϸ߷��������Ĺ�����ֲ����ǹ������ϵͣ��ǳ��ʺ�ǰ��ʹ�ã���������������õ���һ������";
		cardInfomation[10] = "�����涼�ܲ����ֲ����Գ䵱ս���е�������������������������ɵõ�����";
		cardInfomation[11] = "�����涼�ܲ����ֲ����Գ䵱ս���е�������������������������ɵõ�����";
		cardInfomation[12] = "�����涼�ܲ����ֲ����Գ䵱ս���е�������������������������ɵõ�����";
		cardInfomation[13] = "�����涼�ܲ����ֲ����Գ䵱ս���е�������������������������ɵõ�����";
		cardInfomation[14] = "�����涼�ܲ����ֲ����Գ䵱ս���е�������������������������ɵõ�����";
		cardInfomation[15] = "ӵ�м��칥���ٶȵ�ֲ������󽫽�һ�����������ٶ�";
		cardInfomation[16] = "ӵ�м��칥���ٶȵ�ֲ������󽫽�һ�����������ٶ�";
		cardInfomation[17] = "ӵ�м��칥���ٶȵ�ֲ������󽫽�һ�����������ٶ�";
		cardInfomation[18] = "ӵ�м��칥���ٶȵ�ֲ������󽫽�һ�����������ٶ�";
		cardInfomation[19] = "ӵ�м��칥���ٶȵ�ֲ������󽫽�һ�����������ٶ�";
		cardInfomation[20] = "���з������ӵ�������ֲ�������������ӷ����ӵ�������";
		cardInfomation[21] = "���з������ӵ�������ֲ�������������ӷ����ӵ�������";
		cardInfomation[22] = "���з������ӵ�������ֲ�������������ӷ����ӵ�������";
		cardInfomation[23] = "���з������ӵ�������ֲ�������������ӷ����ӵ�������";
		cardInfomation[24] = "���з������ӵ�������ֲ�������������ӷ����ӵ�������";
		cardInfomation[25] = "С�ͷ�����ֲ�����������������������ֵ";
		cardInfomation[26] = "С�ͷ�����ֲ�����������������������ֵ";
		cardInfomation[27] = "С�ͷ�����ֲ�����������������������ֵ";
		cardInfomation[28] = "С�ͷ�����ֲ�����������������������ֵ";
		cardInfomation[29] = "С�ͷ�����ֲ�����������������������ֵ";
		cardInfomation[30] = "���ͷ�����ֲ������󽫴������������������ֵ";
		cardInfomation[31] = "���ͷ�����ֲ������󽫴������������������ֵ";
		cardInfomation[32] = "���ͷ�����ֲ������󽫴������������������ֵ";
		cardInfomation[33] = "���ͷ�����ֲ������󽫴������������������ֵ";
		cardInfomation[34] = "���ͷ�����ֲ������󽫴������������������ֵ";
		cardInfomation[35] = "ӵ��������Χ�ĸ�ֲ�﹥����������ֲ������󽫽�һ��������ӵĹ�����";
		cardInfomation[36] = "ӵ��������Χ�ĸ�ֲ�﹥����������ֲ������󽫽�һ��������ӵĹ�����";
		cardInfomation[37] = "ӵ��������Χ�ĸ�ֲ�﹥����������ֲ������󽫽�һ��������ӵĹ�����";
		cardInfomation[38] = "ӵ��������Χ�ĸ�ֲ�﹥����������ֲ������󽫽�һ��������ӵĹ�����";
		cardInfomation[39] = "ӵ��������Χ�ĸ�ֲ�﹥����������ֲ������󽫽�һ��������ӵĹ�����";
		cardInfomation[40] = "ӵ��������Χ�ĸ�ֲ�������������ֲ������󽫽�һ��������ӵķ�����";
		cardInfomation[41] = "ӵ��������Χ�ĸ�ֲ�������������ֲ������󽫽�һ��������ӵķ�����";
		cardInfomation[42] = "ӵ��������Χ�ĸ�ֲ�������������ֲ������󽫽�һ��������ӵķ�����";
		cardInfomation[43] = "ӵ��������Χ�ĸ�ֲ�������������ֲ������󽫽�һ��������ӵķ�����";
		cardInfomation[44] = "ӵ��������Χ�ĸ�ֲ�������������ֲ������󽫽�һ��������ӵķ�����";
		cardInfomation[45] = "ӵ��������Χ�ĸ�ֲ�﹥���ٶȵ�������ֲ������󽫽�һ��������ӵĹ����ٶ�";
		cardInfomation[46] = "ӵ��������Χ�ĸ�ֲ�﹥���ٶȵ�������ֲ������󽫽�һ��������ӵĹ����ٶ�";
		cardInfomation[47] = "ӵ��������Χ�ĸ�ֲ�﹥���ٶȵ�������ֲ������󽫽�һ��������ӵĹ����ٶ�";
		cardInfomation[48] = "ӵ��������Χ�ĸ�ֲ�﹥���ٶȵ�������ֲ������󽫽�һ��������ӵĹ����ٶ�";
		cardInfomation[49] = "ӵ��������Χ�ĸ�ֲ�﹥���ٶȵ�������ֲ������󽫽�һ��������ӵĹ����ٶ�";
		cardInfomation[50] = "ÿ��һ��ʱ�����������Χ�ĸ�ֲ��һ��������ֵ�������󽫽�һ��������ӵ�����ֵ";
		cardInfomation[51] = "ÿ��һ��ʱ�����������Χ�ĸ�ֲ��һ��������ֵ�������󽫽�һ��������ӵ�����ֵ";
		cardInfomation[52] = "ÿ��һ��ʱ�����������Χ�ĸ�ֲ��һ��������ֵ�������󽫽�һ��������ӵ�����ֵ";
		cardInfomation[53] = "ÿ��һ��ʱ�����������Χ�ĸ�ֲ��һ��������ֵ�������󽫽�һ��������ӵ�����ֵ";
		cardInfomation[54] = "ÿ��һ��ʱ�����������Χ�ĸ�ֲ��һ��������ֵ�������󽫽�һ��������ӵ�����ֵ";
		cardInfomation[55] = "�ܹ����������߹�����ֲ�������ֲ���ܹ�����ֲ��ķ���Ƕȣ������󽫴����߻���ֵ";
		cardInfomation[56] = "�ܹ����������߹�����ֲ�������ֲ���ܹ�����ֲ��ķ���Ƕȣ������󽫴����߻���ֵ";
		cardInfomation[57] = "�ܹ����������߹�����ֲ�������ֲ���ܹ�����ֲ��ķ���Ƕȣ������󽫴����߻���ֵ";
		cardInfomation[58] = "�ܹ����������߹�����ֲ�������ֲ���ܹ�����ֲ��ķ���Ƕȣ������󽫴����߻���ֵ";
		cardInfomation[59] = "�ܹ����������߹�����ֲ�������ֲ���ܹ�����ֲ��ķ���Ƕȣ������󽫴����߻���ֵ";
		cardInfomation[60] = "�ܹ���һ��ֱ���ϵ�ֲ����й�������������߹�����Χ�͹�����";
		cardInfomation[61] = "�ܹ���һ��ֱ���ϵ�ֲ����й�������������߹�����Χ�͹�����";
		cardInfomation[62] = "�ܹ���һ��ֱ���ϵ�ֲ����й�������������߹�����Χ�͹�����";
		cardInfomation[63] = "�ܹ���һ��ֱ���ϵ�ֲ����й�������������߹�����Χ�͹�����";
		cardInfomation[64] = "�ܹ���һ��ֱ���ϵ�ֲ����й�������������߹�����Χ�͹�����";
		cardInfomation[65] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ��Ч�Ĵ����ÿ�δ������һ�����ʵ���ѪЧ���������������Ѫ�ı���";
		cardInfomation[66] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ��Ч�Ĵ����ÿ�δ������һ�����ʵ���ѪЧ���������������Ѫ�ı���";
		cardInfomation[67] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ��Ч�Ĵ����ÿ�δ������һ�����ʵ���ѪЧ���������������Ѫ�ı���";
		cardInfomation[68] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ��Ч�Ĵ����ÿ�δ������һ�����ʵ���ѪЧ���������������Ѫ�ı���";
		cardInfomation[69] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ��Ч�Ĵ����ÿ�δ������һ�����ʵ���ѪЧ���������������Ѫ�ı���";
		cardInfomation[70] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ�����ÿ���ܵ���ʬ�Ĺ����ᷴ��һ�����ʵ��˺�����������߷����ı���";
		cardInfomation[71] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ�����ÿ���ܵ���ʬ�Ĺ����ᷴ��һ�����ʵ��˺�����������߷����ı���";
		cardInfomation[72] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ�����ÿ���ܵ���ʬ�Ĺ����ᷴ��һ�����ʵ��˺�����������߷����ı���";
		cardInfomation[73] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ�����ÿ���ܵ���ʬ�Ĺ����ᷴ��һ�����ʵ��˺�����������߷����ı���";
		cardInfomation[74] = "һ�ַ��ط����͵�ֲ��ܶ������ĵ���ʵʩ�����ÿ���ܵ���ʬ�Ĺ����ᷴ��һ�����ʵ��˺�����������߷����ı���";
		cardInfomation[75] = "ÿ��һ��ʱ���ܵ���һ�����Բɼ���̫���������������߲ɼ���̫����";
		cardInfomation[76] = "ÿ��һ��ʱ���ܵ���һ�����Բɼ���̫���������������߲ɼ���̫����";
		cardInfomation[77] = "ÿ��һ��ʱ���ܵ���һ�����Բɼ���̫���������������߲ɼ���̫����";
		cardInfomation[78] = "ÿ��һ��ʱ���ܵ���һ�����Բɼ���̫���������������߲ɼ���̫����";
		cardInfomation[79] = "ÿ��һ��ʱ���ܵ���һ�����Բɼ���̫���������������߲ɼ���̫����";
		cardInfomation[80] = "ÿ��һ��ʱ���Զ��õ�һ��������̫���������������ߵõ���̫����";
		cardInfomation[81] = "ÿ��һ��ʱ���Զ��õ�һ��������̫���������������ߵõ���̫����";
		cardInfomation[82] = "ÿ��һ��ʱ���Զ��õ�һ��������̫���������������ߵõ���̫����";
		cardInfomation[83] = "ÿ��һ��ʱ���Զ��õ�һ��������̫���������������ߵõ���̫����";
		cardInfomation[84] = "ÿ��һ��ʱ���Զ��õ�һ��������̫���������������ߵõ���̫����";
		cardInfomation[85] = "��ʹ�ܵ������Ľ�ʬ�����ƶ��ٶȣ���һ���Ĺ���������������Խ�һ���������Ч��������";
		cardInfomation[86] = "��ʹ�ܵ������Ľ�ʬ�����ƶ��ٶȣ���һ���Ĺ���������������Խ�һ���������Ч��������";
		cardInfomation[87] = "��ʹ�ܵ������Ľ�ʬ�����ƶ��ٶȣ���һ���Ĺ���������������Խ�һ���������Ч��������";
		cardInfomation[88] = "��ʹ�ܵ������Ľ�ʬ�����ƶ��ٶȣ���һ���Ĺ���������������Խ�һ���������Ч��������";
		cardInfomation[89] = "��ʹ�ܵ������Ľ�ʬ�����ƶ��ٶȣ���һ���Ĺ���������������Խ�һ���������Ч��������";
		cardInfomation[90] = "��ʹ�ܵ������Ľ�ʬ���������ٶȣ���һ���ķ���������������Խ�һ���������Ч��������";
		cardInfomation[91] = "��ʹ�ܵ������Ľ�ʬ���������ٶȣ���һ���ķ���������������Խ�һ���������Ч��������";
		cardInfomation[92] = "��ʹ�ܵ������Ľ�ʬ���������ٶȣ���һ���ķ���������������Խ�һ���������Ч��������";
		cardInfomation[93] = "��ʹ�ܵ������Ľ�ʬ���������ٶȣ���һ���ķ���������������Խ�һ���������Ч��������";
		cardInfomation[94] = "��ʹ�ܵ������Ľ�ʬ���������ٶȣ���һ���ķ���������������Խ�һ���������Ч��������";
		cardInfomation[95] = "һ������ȫ��λ����������ֲ������󽫽�һ����߹�������������";
		cardInfomation[96] = "һ������ȫ��λ����������ֲ������󽫽�һ����߹�������������";
		cardInfomation[97] = "һ������ȫ��λ����������ֲ������󽫽�һ����߹�������������";
		cardInfomation[98] = "һ������ȫ��λ����������ֲ������󽫽�һ����߹�������������";
		cardInfomation[99] = "һ������ȫ��λ����������ֲ������󽫽�һ����߹�������������";
		cardInfomation[100] = "һ�����м��̿�Ƭ����ʱ��ֲ������󽫽�һ�����ٿ�Ƭ����ʱ��";
		cardInfomation[101] = "һ�����м��̿�Ƭ����ʱ��ֲ������󽫽�һ�����ٿ�Ƭ����ʱ��";
		cardInfomation[102] = "һ�����м��̿�Ƭ����ʱ��ֲ������󽫽�һ�����ٿ�Ƭ����ʱ��";
		cardInfomation[103] = "һ�����м��̿�Ƭ����ʱ��ֲ������󽫽�һ�����ٿ�Ƭ����ʱ��";
		cardInfomation[104] = "һ�����м��̿�Ƭ����ʱ��ֲ������󽫽�һ�����ٿ�Ƭ����ʱ��";
		cardInfomation[105] = "ӵ�м��߹�������ֲ���������ֵ���٣�������Ҳ�ϵͣ������󽫽�һ����߹�����";
		cardInfomation[106] = "ӵ�м��߹�������ֲ���������ֵ���٣�������Ҳ�ϵͣ������󽫽�һ����߹�����";
		cardInfomation[107] = "ӵ�м��߹�������ֲ���������ֵ���٣�������Ҳ�ϵͣ������󽫽�һ����߹�����";
		cardInfomation[108] = "ӵ�м��߹�������ֲ���������ֵ���٣�������Ҳ�ϵͣ������󽫽�һ����߹�����";
		cardInfomation[109] = "ӵ�м��߹�������ֲ���������ֵ���٣�������Ҳ�ϵͣ������󽫽�һ����߹�����";
		cardInfomation[110] = "�ܶ�һ����Χ�ڵĵ��ˣ�˲����ɾ޴���˺��������󽫽�һ������˺���";
		cardInfomation[111] = "�ܶ�һ����Χ�ڵĵ��ˣ�˲����ɾ޴���˺��������󽫽�һ������˺���";
		cardInfomation[112] = "�ܶ�һ����Χ�ڵĵ��ˣ�˲����ɾ޴���˺��������󽫽�һ������˺���";
		cardInfomation[113] = "�ܶ�һ����Χ�ڵĵ��ˣ�˲����ɾ޴���˺��������󽫽�һ������˺���";
		cardInfomation[114] = "�ܶ�һ����Χ�ڵĵ��ˣ�˲����ɾ޴���˺��������󽫽�һ������˺���";
		cardInfomation[115] = "���пֲ�ɱ�����ĵ��ף���û�г�����֮ǰ��������������󽫽�һ������˺���";
		cardInfomation[116] = "���пֲ�ɱ�����ĵ��ף���û�г�����֮ǰ��������������󽫽�һ������˺���";
		cardInfomation[117] = "���пֲ�ɱ�����ĵ��ף���û�г�����֮ǰ��������������󽫽�һ������˺���";
		cardInfomation[118] = "���пֲ�ɱ�����ĵ��ף���û�г�����֮ǰ��������������󽫽�һ������˺���";
		cardInfomation[119] = "���пֲ�ɱ�����ĵ��ף���û�г�����֮ǰ��������������󽫽�һ������˺���";
		cardInfomation[120] = "���ӵ��˽����ĵش̣��ܶԵ������һ���������˺��������󽫽�һ������˺�����";
		cardInfomation[121] = "���ӵ��˽����ĵش̣��ܶԵ������һ���������˺��������󽫽�һ������˺�����";
		cardInfomation[122] = "���ӵ��˽����ĵش̣��ܶԵ������һ���������˺��������󽫽�һ������˺�����";
		cardInfomation[123] = "���ӵ��˽����ĵش̣��ܶԵ������һ���������˺��������󽫽�һ������˺�����";
		cardInfomation[124] = "���ӵ��˽����ĵش̣��ܶԵ������һ���������˺��������󽫽�һ������˺�����";
		
		cardInfomation[125] = "����һ��ʱ��ľ����󣬿��Ե���ͷų�ǿ��Ļ��棬��������Լ��̾���ʱ��";
		cardInfomation[126] = "����һ��ʱ��ľ����󣬿��Ե���ͷų�ǿ��Ļ��棬��������Լ��̾���ʱ��";
		cardInfomation[127] = "����һ��ʱ��ľ����󣬿��Ե���ͷų�ǿ��Ļ��棬��������Լ��̾���ʱ��";
		cardInfomation[128] = "����һ��ʱ��ľ����󣬿��Ե���ͷų�ǿ��Ļ��棬��������Լ��̾���ʱ��";
		cardInfomation[129] = "����һ��ʱ��ľ����󣬿��Ե���ͷų�ǿ��Ļ��棬��������Լ��̾���ʱ��";
		
		cardInfomation[130] = "����һ��ʱ��ľ����󣬿��Ե���ͷų���ǿ�Ĺ��ߣ���������Լ��̾���ʱ��";
		cardInfomation[131] = "����һ��ʱ��ľ����󣬿��Ե���ͷų���ǿ�Ĺ��ߣ���������Լ��̾���ʱ��";
		cardInfomation[132] = "����һ��ʱ��ľ����󣬿��Ե���ͷų���ǿ�Ĺ��ߣ���������Լ��̾���ʱ��";
		cardInfomation[133] = "����һ��ʱ��ľ����󣬿��Ե���ͷų���ǿ�Ĺ��ߣ���������Լ��̾���ʱ��";
		cardInfomation[134] = "����һ��ʱ��ľ����󣬿��Ե���ͷų���ǿ�Ĺ��ߣ���������Լ��̾���ʱ��";
		
		
		cardInfomation[300] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺϳ���ʹ�ã�������ȫ����������";
		cardInfomation[301] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺϳ���ʹ�ã�������ȫ����������";
		cardInfomation[302] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺϳ���ʹ�ã�������ȫ����������";
		cardInfomation[303] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺϳ���ʹ�ã�������ȫ����������";
		cardInfomation[304] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺϳ���ʹ�ã�������ȫ����������";
		cardInfomation[305] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺ�����ʹ�ã�������ȫ����������";
		cardInfomation[306] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺ�����ʹ�ã�������ȫ����������";
		cardInfomation[307] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺ�����ʹ�ã�������ȫ����������";
		cardInfomation[308] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺ�����ʹ�ã�������ȫ����������";
		cardInfomation[309] = "�����������Ƚ�ƽ���Ľ�ʬ���ʺ�����ʹ�ã�������ȫ����������";
		cardInfomation[310] = "�������ǳ���Ľ�ʬ���ʺϵ����ʹ�ã��������������������";
		cardInfomation[311] = "�������ǳ���Ľ�ʬ���ʺϵ����ʹ�ã��������������������";
		cardInfomation[312] = "�������ǳ���Ľ�ʬ���ʺϵ����ʹ�ã��������������������";
		cardInfomation[313] = "�������ǳ���Ľ�ʬ���ʺϵ����ʹ�ã��������������������";
		cardInfomation[314] = "�������ǳ���Ľ�ʬ���ʺϵ����ʹ�ã��������������������";
		cardInfomation[315] = "ӵ�д��������Ľ�ʬ�������������ߴ��̵�Ч��";
		cardInfomation[316] = "ӵ�д��������Ľ�ʬ�������������ߴ��̵�Ч��";
		cardInfomation[317] = "ӵ�д��������Ľ�ʬ�������������ߴ��̵�Ч��";
		cardInfomation[318] = "ӵ�д��������Ľ�ʬ�������������ߴ��̵�Ч��";
		cardInfomation[319] = "ӵ�д��������Ľ�ʬ�������������ߴ��̵�Ч��";
		cardInfomation[320] = "ӵ������һ�������Ľ�ʬ��������һ�����ʴ���˫�������������������ߴ�������";
		cardInfomation[321] = "ӵ������һ�������Ľ�ʬ��������һ�����ʴ���˫�������������������ߴ�������";
		cardInfomation[322] = "ӵ������һ�������Ľ�ʬ��������һ�����ʴ���˫�������������������ߴ�������";
		cardInfomation[323] = "ӵ������һ�������Ľ�ʬ��������һ�����ʴ���˫�������������������ߴ�������";
		cardInfomation[324] = "ӵ������һ�������Ľ�ʬ��������һ�����ʴ���˫�������������������ߴ�������";
	
	}

}
