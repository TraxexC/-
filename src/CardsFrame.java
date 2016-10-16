
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.loon.framework.javase.game.core.graphics.LFont;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;

public class CardsFrame {
	private Main main = null;
	static int[] cardRangeID = null; // ��Ƭ���п�Ƭλ�ö�Ӧ�Ŀ�Ƭid
	private static int CARDSNUM = 10; // ��Ͽ�Ƭ���еĿ�Ƭ����
	private static final int CARDY = 8; // ��Ͽ��п�Ƭ��y����
	private static int CARD1PX = 78;// ��һ����Ƭ���õ�x����
	private static final int CARDWIDTH = 50;// ��Ƭ�Ŀ�
	private static final int CARDHIGHT = 70;// ��Ƭ�ĸ�
	private static final int CARDSUNNUMX = 12, CARDSUNNUMY = 65 + CARDY;// ����Ƭ��̫��������ƫ��
	private static final int CARDSTARX = 10, CARDSTARY = 10;// ����Ƭ�ļ���������ƫ��
	private static final int CARDUPX = 33, CARDUPY = 63;// ����Ƭ�������������ƫ��
	private static int upTimer;
	private static final LImage star = Resource.getStar(); // ��Ƭ����ͼƬ
	private int cdPicHight = 0; // cdͼƬ�ĸ�
	private float cdTime = 0; // ��Ƭ��cdʱ��
	private float cd = 0; // ��Ƭ�Ѿ�������cdʱ��
	private boolean cdOK = false; // ��Ƭcd�Ƿ��Ѿ�����
	private boolean uptem = false; // ��Ƭ���������Ŀ���
	private LImage mouseImage = null;
	private int miWidth = 0; // ���ͼƬ�Ŀ�
	private int miHight = 0; // ���ͼƬ�ĸ�
	private int miX = 0; // ���ͼƬ��X
	private int miY = 0; // ���ͼƬ��Y
	private int temnum = 0;
	private int cardID;
	private int objectID;
	private int xr,yr ; //����ӰͼƬ��x,yƫ��
	private StringBuffer  sbi = new StringBuffer("������Ҫ̫����");
	private StringBuffer  sb = new StringBuffer();
	private static final String s = "������Ҫ̫��";
	private static final String s1 = "�һ���Ƭ����";
	private static final LFont l = new LFont("GulimChe", 0, 11); 
	private static final LFont ls = new LFont("����_GB2312", LFont.STYLE_BOLD, 20);
	List<Card> allCards = new ArrayList<Card>();

	public CardsFrame(Main main) {
		this.main = main;
		CARD1PX += main.MCFX;
		initCardID();
	}

	// ����Ƭ��cdͼ��
	public void draw(LGraphics g) {
		//���������������
		this.upTimer++;
		if (this.upTimer == 20) {
			this.uptem = !this.uptem;
			upTimer = 0;
		}
		for (int i = 0; i < allCards.size(); i++) {
			Card temCard = allCards.get(i);
			this.cdTime = temCard.getCdTime();
			cd = temCard.getCd();
			temCard.jugdeUp();
			// ����Ƭ�ľ�̬ͼƬ
			g.drawImage(temCard.getCardImage(), CARD1PX + (i * 50), CARDY);

			// ����Ƭ�����̫����
			g.setFont(l);
			g.setColor(Color.BLACK);
			sb.delete(0, sb.length());
			sb.append(temCard.getSunNum());
			g.drawString(sb.toString(), CARD1PX + (i * 50) + CARDSUNNUMX,
					CARDSUNNUMY);
			// ����Ƭ���������
			if (temCard.isUpAble() && this.uptem && temCard.isCdOK()) {
				g.drawImage(Resource.getUpStar(), CARD1PX + (i * 50) + CARDUPX,
						CARDUPY);
			}

			// ����Ƭ����ͼ��

			g.scale(0.5, 0.5);
			for (int j = 0; j < temCard.getLv(); j++) {
				g.drawImage(star, (CARD1PX + (i * 50) + j * CARDSTARX) * 2,
						CARDY + 2);
			}
			g.scale(2, 2);

			// ������Ƭʱ�Ŀ�Ƭͼ��
			if (cdTime == cd && Main.mySunNum >= temCard.getSunNum()) {
				temCard.setCdOK(true);
				if (main.cfRange == i && main.isHoldCard) {
					main.holdCardRange = main.cfRange;
					double d = g.getAlpha();
					g.setAlpha(0.6F);
					g.rectFill(CARD1PX + (i * 50), CARDY, CARDWIDTH, CARDHIGHT,
							Color.BLACK);
					g.setAlpha(d);
				}

			} else {
				double d = g.getAlpha();
				g.setAlpha(0.3F);
				g.rectFill(CARD1PX + (i * 50), CARDY, CARDWIDTH, CARDHIGHT,
						Color.BLACK);
				g.setAlpha(0.5F);
				cdPicHight = (int) (CARDHIGHT * ((cdTime - cd) / cdTime));
				g.rectFill(CARD1PX + (i * 50), CARDY, CARDWIDTH, cdPicHight,
						Color.BLACK);
				g.setAlpha(d);
				if (cd < temCard.getCdTime())temCard.setCd(cd + 1);
				temCard.setCdOK(false);
			}
		}
	}

	public List<Card> getAllCards() {
		return allCards;
	}

	// �õ�����ڿ�Ƭ���е��ƶ���λ��
	public void getRange(int x, int y) {

		if (y > CARDY && y < CARDY + 70) {

			if (x > 595 && x < 665) {
				main.cfRange = -2;
				return;
			}
			for (int i = 0; i < allCards.size(); i++) {
				temnum = CARD1PX + (i * 50);
				if (x > temnum && x < temnum + 50) {
					main.cfRange = i;
					return;
				}

			}
			main.cfRange = -1;
		} else {

			main.cfRange = -1;
		}
	}

	// ��ö�Ӧ��Ƭ�����ͼƬ
	public void drawMousePic(int cfRange, int x, int y, LGraphics g) {
		if (cfRange != -2) {
			// ����˿�Ƭ�ǹ���
			cardID = cardRangeID[cfRange];
			objectID=this.allCards.get(cfRange).getObjectID();
			if(objectID>300){
				int  animationID=Resource.getZombieInfo(objectID-300)[3];
				mouseImage = Resource.getZombieAnimation(animationID).getSpriteImage(0).getLImage();

			}else{

				int  animationID=Resource.getPlantInfo(objectID)[4];
				mouseImage =Resource.getPlantAnimation(animationID).getSpriteImage(0).getLImage();	
			}
		} else {
			// ��ɨ�ѵ����ͼƬ
			mouseImage = Resource.getScoop1();
		}

		miWidth = mouseImage.getWidth();
		miHight = mouseImage.getHeight();
		// ��ߣ��ұ߻���ͬ�ľ���ͼ�� �������ֲ���ͼ���෴
		if (cfRange == -2) {
			miX = x - miWidth / 2;
			miY = y - miHight / 2;
			g.drawImage(mouseImage, miX, miY);
			return;
		}
		
		int[] i;
		if(objectID<=300 ){
			 i = Resource.getPlantInfo(objectID);
		}else{
			 i = Resource.getZombieInfo(objectID-300);
		}
			int xr=i[22];
			int yr;
			if(!Main.isLeft){
				if(xr!=0)xr= miWidth-i[22];	
			}
			yr=i[23];	
			if(xr==0){
				miX = x - miWidth / 2;
			}else{
				miX=x-xr;
			}
			if(yr==0){
				miY = y - miHight / 2;
			}else{
				miY=y-yr;
			}
				
		
		if (Main.isLeft) {
				g.drawImage(mouseImage, miX, miY);
		} else {
				g.drawMirrorImage(mouseImage, miX, miY);
			
		}

	}

	private void initCardID() {
		
		int[] i =main.choosedCard;
		this.cardRangeID = i;
		allCards.add(new Card(i[0]));
		allCards.add(new Card(i[1]));
		allCards.add(new Card(i[2]));
		allCards.add(new Card(i[3]));
		allCards.add(new Card(i[4]));
		allCards.add(new Card(i[5]));
		allCards.add(new Card(i[6]));
		allCards.add(new Card(i[7]));
		allCards.add(new Card(i[8]));
		allCards.add(new Card(i[9]));
	}

	public void drawScoop(LGraphics g) {
		g.drawImage(Resource.getScoopFrame(), 595, 0);
		if (main.holdCardRange != -2) {
			g.drawImage(Resource.getScoop1(), 590, -8);
		}

	}
  //����Ƭ����ʱ��ħ��Ч��
   public void drawUpMagic(){
	   int i = Main.cfRange;
	  new Magic(601, (i * 50)+60,-10,main);

   }

   //����Ƭ����Ϣ��ʾ
   public void drawCardInfo(LGraphics g){
	   
	   if(Main.cfRange!=-1 && Main.cfRange!=-2){
		  Card c =  this.allCards.get(Main.cfRange);
		LImage image = c.getCardImage();
		g.setAlpha(0.5f);
		   g.fillRect(665, 0, 135, 200); 
		   g.setAlpha(1f);
		   Color t = g.getColor();
		   LFont f = g.getLFont();
		   g.drawImage(image, 670,0);
		   g.setColor(Color.RED);
		   g.drawString(s1, 720, 20);
		   g.drawString(s.toString(), 720, 40);
		   sbi.delete(0, sbi.length());
		   sbi.append(c.getUpNum());
		   g.setColor(Color.YELLOW);
		   g.drawString(sbi.toString(), 750, 60);
		  Resource.cutString(g, c.getInfomation(), 665, 80, 250,ls,Color.BLUE);
		  g.setColor(t);
		  g.setFont(f);
		  
	   }
	    
	   
   }
   
   
}
