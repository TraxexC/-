import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.loon.framework.javase.game.GameScene;
import org.loon.framework.javase.game.action.sprite.ISprite;
import org.loon.framework.javase.game.action.sprite.Sprite;
import org.loon.framework.javase.game.core.LSystem;
import org.loon.framework.javase.game.core.graphics.Deploy;
import org.loon.framework.javase.game.core.graphics.LFont;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.Screen;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;
import org.loon.framework.javase.game.core.graphics.window.LText;
import org.loon.framework.javase.game.core.timer.LTimer;

public class Main extends Screen implements Runnable {
	static int netID;
	static String IP;
	static int udpPort;
	static boolean action;
	static String netName;
	static int port;
	static int mySunNum = 100;// ̫����
	static Bullets bullets = null;
	static Plants plants = null;
	static Zombies zombies = null;
	static NetMsgs netMsgs = null;
	static Robot robot =null;
	static NetClient nc = null;
	static Drop drop = null;
	static Fields fields = null;
	static boolean isLeft = true;
	static boolean isHoldCard = false;// ����Ƭ���
	static boolean begin, chooseBegin; // ��Ϸ�Ƿ���ʽ��ʼ,���Ƿ�ѡ���꿨Ƭ�Ŀ���
	static int holdCardRange = -1; // ����Ƭ�ڿ�Ƭ���е�id
	static int cfRange = -1; // ������ƶ�����Ƭ�������е�λ��
	public static int mpx; // ����ͼƬ��ʵ������
	static int sx, sy = 0; // �������Ļ�е�����
	private static int mx = 0; // ����ڵ�ͼ�ϵ�����
	private static int fieldID = -1; // ��ǰ������ڵ����ؿ�id
	static final int MCFX = 10, MCFY = 0;// ��Ͽ�Ƭ�������
	private static final int MOUSER = 2;// ������Ҿ���
	private static final int MOUSEL = 1;// ����������
	private static final LFont l = new LFont("Arial Black", 0, 12); // ����̫����������
	private static final LFont chatFont = new LFont("����_GB2312", 0, 15); // ����̫����������
	private static final int MAPMOVEVALUE = 25;// ÿ�ε�ͼ����ľ���
	private static LImage bgp = Resource.getBackGroundPic(); // ������ͼƬ
	private static LImage mcf = Resource.getMixCardFrame(); // ��Ͽ�Ƭ��ͼƬ
	private static StringBuffer sunNum = new StringBuffer();
	CardsFrame cf = null;
	private boolean init = false;
	List<ObjectIn> allSprites = new ArrayList<ObjectIn>();
	boolean clickObject;// �ж��Ƿ���������
	private StringBuffer sbi = new StringBuffer("a");
	private int chooseID; // ѡ��Ŀ�Ƭid
	int[] choosedCard = new int[10]; // ��ѡ���10����Ƭ
	private int chooseTimer = 200; // ѡ��Ƭ��ʱ��Ϊ30��
	private LText chatText = new LText("",20,500,700,20); //�����
	private boolean  openChatText ; //�ж�������Ƿ��Ѿ���
	private List<ChatMsg> chatTextList= new ArrayList<ChatMsg>();
	private static int chatNetID=0;
	private int chatDeleteTimer; //���������¼����ʧ
	static boolean hasRobot;
	private LTimer sunTimer = new LTimer(5000);
	private boolean  normalWindow; //�˴����Ƿ�Ϊ���ڻ�
	// ��ʱ��������

	// ��ʱ��������
	public Main() {
		new SetFrame(this);
		Resource.readInfo();
		this.updateFullScreen();
		plants = new Plants(this);
		bullets = new Bullets(this);
		zombies = new Zombies(this);
		netMsgs = new NetMsgs(this);
		nc = new NetClient(this);
		nc.setUdpPort(udpPort);
		nc.connect(IP, port);
		new Thread(this).start();
		chatText.setVisible(false);
		chatText.setBackground(Color.white);
		chatText.setAlpha(0.3f);
		this.add(chatText);
		
	}

	public static void main(String[] args) {
		GameScene frame = new GameScene("ֲ���ս��ʬ���ǰ�", 800, 600);
		Deploy deploy = frame.getDeploy();
		deploy.setFPS(10);
		deploy.setScreen(new Main());
		deploy.setShowFPS(true);
		deploy.setLogo(false);
		deploy.mainLoop();
		frame.setCursor("image/cursors1.png");
		frame.setIconImage("image/icon.png");
		frame.showFrame();
		
	}

	@Override
	public void draw(LGraphics g) {
		if (begin) {
			if (init == false) {
				fields = new Fields(this);
				cf = new CardsFrame(this);
				if (!Main.isLeft)
					Main.mpx = -1500;
				init = true;
				if(this.hasRobot)this.robot = new Robot(this);
				this.initBrain();
			}
			g.drawImage(bgp, mpx, 0);
			g.drawImage(mcf, MCFX, MCFY);
			cf.drawScoop(g);
			cf.drawCardInfo(g);
			cf.draw(g);
			drawString(g);
			drawMousePic(g);
			this.sortSpites(g);
			Main.bullets.drawBullet(g);
			Drop.drawAllDrops(g);
			if(Main.hasRobot)robot.drawInfo(g);
		} else {
			if (!Main.chooseBegin) {
				g.drawImage(Resource.getMaingroundpic(), 0, 0);
				netMsgs.drawLoginString(g);
				SynchronizedMsg sm = new SynchronizedMsg(SynchronizedMsg.NETMSGACTION,Main.action,Main.isLeft,this);
				this.nc.send(sm);
			} else {
				g.drawImage(Resource.getChooseImage(), 0, 0);
				drawChooseCard(g);
			}
		}
		//�����������
		this.drawChatContent(g);
	}
	//�����������
	public void  drawChatContent(LGraphics g) {
		//���������¼����ʧ
		this.chatDeleteTimer++;
		if(this.chatDeleteTimer>=150){
			if(this.chatTextList.size()!=0){
				this.chatTextList.remove(this.chatTextList.size()-1);
			}
				this.chatDeleteTimer=0;
		}
		
		//������Ϣͬ��
		synchronized(ChatMsg.chatList){
			Iterator<ChatMsg> ite = ChatMsg.chatList.iterator();
			while (ite.hasNext()) {
				ChatMsg cm = (ChatMsg) ite.next();
				this.chatTextList.add(0,cm);
					ite.remove();
			}
		}
		
		int t= 0;
	LFont lf = g.getLFont();
	Color c = g.getColor();
	g.setColor(Color.BLACK);
	g.setFont(this.chatFont);	
		
	for(ChatMsg s : this.chatTextList){
		 t++;
		 String sa=s.getTextContent() ;
		 if(!sa.equals("")){
			 g.drawString(s.getNetName()+":"+sa,20,500-t*20);		
		 }
	}
	g.setFont(lf);
	g.setColor(c);
	
	}
	
	// �����Զ�ѡ��Ƭ
	private void autoChoose() {
		for (int i = 0; i < this.choosedCard.length; i++) {
			if (this.choosedCard[i] == 0) {
				for (int t = 1; t < this.choosedCard.length + 1; t++) {
					if (this.checkExistCard(t) == -1) {
						this.choosedCard[i] = t;
						break;
					}
				}
			}
		}

	}

	// ��ѡ��Ƭ�Ŀ�Ƭ
	private void drawChooseCard(LGraphics g) {
		this.chooseTimer--;
		if (this.chooseTimer <= 0) {
			this.autoChoose();
			this.begin = true;
			this.setFPS(30);
		}
		LFont l = new LFont("GulimChe", 0, 11);
		LFont f = g.getLFont();
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.setFont(l);
		g.drawImage(mcf, MCFX, MCFY);
		for (int j = 0; j < this.choosedCard.length; j++) {
			if (this.choosedCard[j] != 0) {
				int imageID = Resource.getCardInfo(this.choosedCard[j])[15];
				LImage image = Resource.getCardImage(imageID);
				g.drawImage(image, 85 + (j * 50), 8);
			}
		}
		boolean b = false;
		for (int i = 0; i < Resource.getCardInfo().size(); i++) {
			int[] info = Resource.getCardInfo(i + 1);
			int cardID = info[0];
			int imageID = info[15];
			int sunNum = info[6];
			sbi.delete(0, sbi.length());
			sbi.append(sunNum);
			LImage image = Resource.getCardImage(imageID);
			int t = i / 10;
			int w = i;
			if (9 * t != 0) {
				if (w > 9 * t)
					w -= 9 * t + 1 * t;
			}
			g.drawImage(image, 24 + (w * 52), 90 + (t * 79));
			g.drawString(sbi.toString(), 38 + (w * 52), 155 + (t * 79));
			if (this.checkExistCard(cardID) != -1) {
				double d = g.getAlpha();
				g.setAlpha(0.6F);
				g.rectFill(24 + (w * 52), 90 + (t * 79), 50, 70, Color.BLACK);
				g.setAlpha(d);
			}
			int checkID = getChooseCardID(24 + (w * 52), 90 + (t * 79));
			if (checkID != -1 && !b) {
				this.chooseID = cardID;
				b = true;
			}
		}
		if (!b)
			this.chooseID = -1;
		sbi.delete(0, sbi.length());
		sbi.append("��ѡ��Ƭ��" + this.chooseTimer / 10 + "�����Ϸ��ʼ");
		LFont lf = new LFont("����_GB2312", LFont.STYLE_BOLD, 25);
		g.setFont(lf);
		g.setColor(Color.DARK_GRAY);
		g.drawString(sbi.toString(), 85, 580);
		g.setFont(f);
		g.setColor(c);
	}

	// �������Ŀ�Ƭ����Ƿ�����ѡ�еĿ�Ƭ����
	private int checkExistCard(int t) {
		int result = -1;
		for (int i = 0; i < this.choosedCard.length; i++) {
			if (this.choosedCard[i] == t) {
				result = i;
				break;
			}
		}
		return result;
	}

	private int getChooseCardID(int x, int y) {
		if (x < sx && sx < x + 50 && y < sy && sy < y + 70) {
			return 1;
		}
		return -1;
	}

	private void checkDropClick() {
		ListIterator<Drop> iter = Drop.allDrops.listIterator();
		while (iter.hasNext()) {
			Drop d = iter.next();
			if (this.onClick(d))
				d.beHited();
		}

	}

	@Override
	public void leftClick(MouseEvent e) {
		// �����½����ʱ�ĵ����ж�
		if (!begin) {
			if (!this.chooseBegin) {
				//�������˳���ť
				if(sx>680 && sx<780 && sy>20 && sy<74){
					ClientExitMsg msg=	new ClientExitMsg(this);
					Main.nc.send(msg);
					LSystem.exit();
				}
				// ����ͼ��Ϊ1ʱ��ʾ��������Ϊ0ʱ��ʾ���δ���
				netMsgs.sp.setLayer(1);
				netMsgs.sp2.setLayer(1);
				return;
			} else {
				if (this.chooseID != -1) {
					int minAbleID = -1;
					// ���ѡ�еĿ�Ƭ�Ƿ��Ѿ���ѡ��
					int t = this.checkExistCard(this.chooseID);
					if (t != -1) {
						this.choosedCard[t] = 0;
						return;
					}
					// ����λ����С�±�
					for (int i = 0; i < this.choosedCard.length; i++) {
						if (this.choosedCard[i] == 0) {
							minAbleID = i;
							break;
						}
					}
					if (minAbleID != -1)
						this.choosedCard[minAbleID] = this.chooseID;
				}

			}
			return;

		}

		this.checkDropClick();
		// ����������Ƭ
		if (isHoldCard == false) {
			if (this.clickObject == true) {
				this.clickObject = false;
				fields.redoAllClick();
			} else if // ����object�ĵ���
			(this.fieldID != -1 && !this.clickObject) {
				fields.getAllField().get(fieldID).doClick();
			}
			// ����plant�ĵ���
			if (cfRange == -2) {
				isHoldCard = true;
				Main.holdCardRange = -2;
				return;
			}
			if (cfRange != -1) {
				if (cf.getAllCards().get(cfRange).isCdOK()) {
					isHoldCard = true;
				}
			}

		} else {
			locatePlant();

		}
	}

	@Override
	public void middleClick(MouseEvent arg0) {

	}

	@Override
	public void rightClick(MouseEvent arg0) {
		// �Ҽ�ȡ������
		if (isHoldCard == true) {
			isHoldCard = false;
			fieldID = -1;
			cfRange = -1;
			this.holdCardRange = -1;
		} else {
			// ������Ƭ
			if (cfRange != -1 && cfRange != -2) {
				Card c = cf.getAllCards().get(cfRange);
				if (c.isUpAble() && c.getLv() < 4 && c.isCdOK()) {
					cf.drawUpMagic();
					c.up();
				}
			}
			isHoldCard = false;
			fieldID = -1;
			cfRange = -1;
			this.holdCardRange = -1;
		}
	}

	// �������о���ĸ���

	@Override
	public synchronized void mouseMoved(MouseEvent e) {

		sx = e.getX();
		sy = e.getY();
		runMouseMoved();
	}
	
	@Override
	public void update(long timer) {
		super.update(timer);
		zombies.doAllZombiesUpdate(timer);
		bullets.doAllBulletsUpdate(timer);
		bullets.addNetBullet();
		bullets.SynchronizedBulletMove();
		Magic.doAllMagicsUpdate(timer);
		plants.doAllPlantsUpdate(timer);
		plants.checkPlantRangeAttack();
		plants.addNetPlant();
		plants.plantSyn();
		bullets.checkHit();
		zombies.checkHit();
		zombies.SynchronizedZombie();
		zombies.addNetZombie();
		Drop.updateAllDrops(timer);
		Drop.addNetDrops();
		//��������˵�ˢ��
		 
		if(init && this.hasRobot ){
			if(this.netID == 1 || this.netID ==0)this.robot.freshRobot(timer);	
		}
		if(init && this.hasRobot ){
			this.robot.recordTime();	
		}
		
		//��������Ĺ�궨λ
		if(this.openChatText){
			int length = chatText.getText().length();
			chatText.setCaretPosition(length);
		}
		
	if(this.sunTimer.action(timer))Main.mySunNum+=20;
	}



	public void mapMove(int d) {

		// �����ͼ�ƶ�ʱ��x ƫ��ֵ
		if (d == 2) {
			mpx -= MAPMOVEVALUE;
			plants.mapMove(-MAPMOVEVALUE);
			bullets.mapMove(-MAPMOVEVALUE);
			zombies.mapMove(-MAPMOVEVALUE);
			Drop.mapMoveDrops(-MAPMOVEVALUE);
			Magic.mapMoveMagics(-MAPMOVEVALUE);

		} else {
			mpx += MAPMOVEVALUE;
			plants.mapMove(MAPMOVEVALUE);
			bullets.mapMove(MAPMOVEVALUE);
			zombies.mapMove(MAPMOVEVALUE);
			Drop.mapMoveDrops(MAPMOVEVALUE);
			Magic.mapMoveMagics(MAPMOVEVALUE);
		}

	}

	// ���������е��ַ���
	private void drawString(LGraphics g) {
		g.setColor(Color.BLACK);
		g.setFont(l);
		sunNum.delete(0, sunNum.length());
		sunNum.append(this.mySunNum);
		g.drawString(sunNum.toString(), 20, 74);

	}

	// ��ѡ�п�Ƭʱ�����ͼ��
	private void drawMousePic(LGraphics g) {
		if (isHoldCard == true) {
			cf.drawMousePic(this.cfRange, sx, sy, g);
			if (fieldID != -1 && cfRange != -2)
				fields.drawDImage(this.cfRange, fieldID, mpx, g);
		}

	}

	private void locatePlant() {
		// �����ʱ������Ƭ�������������Ӧ�Ĳݵ���
		if (this.fieldID != -1) {
			Field f = fields.getAllField().get(fieldID);
			// ���������ڲݵؿ��Է���
			if (f.isHandleAble() && holdCardRange != -2 && holdCardRange != -1) {
				fields.getAllField().get(fieldID).locate(this.holdCardRange);
				cf.getAllCards().get(holdCardRange).setCd(0f);
				cf.getAllCards().get(holdCardRange).setCdOK(false);
				holdCardRange = -1;
				isHoldCard = false;
				cfRange = -1;
				fieldID = -1;
			} else {
				if (this.holdCardRange == -2) {
					f.removeObject();
					fields.getAllField().get(fieldID).reDrawAllBuff();
				}
				holdCardRange = -1;
				isHoldCard = false;
				cfRange = -1;
				fieldID = -1;
			}
		}
		holdCardRange = -1;
		isHoldCard = false;
		cfRange = -1;
		fieldID = -1;
	}

	@Override
	public void run() {
		while (true) {
			//�����¿ͻ��˵�ͬ��
			if(!begin){
				ClientNewMsg msg = new ClientNewMsg(this);
				nc.send(msg);	
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}


	private void runMouseMoved() {
		mx = sx - mpx;
		// �ж�����ڿ�Ƭ���ݵ��е�λ��
		if (init) {
			if (Main.isHoldCard == false) {
				cf.getRange(sx, sy);
			}
		
		if (null != this.fields)
			fieldID = fields.getRange(mx, sy);

		// ������
		if (sx > 790 && mpx <= 0 && mpx >= -1580) {
			mapMove(MOUSER);
			this.mouseMove(789, sy);
		}
		if (sx < 10 && mpx < 0) {
			mapMove(MOUSEL);
			this.mouseMove(9, sy);

		}
		}else{
			//�����ƶ����˳���ť��
			if(sx>680 && sx<780 && sy>20 && sy<74){
				Main.netMsgs.setQuitMoved(true);
			}else{
				Main.netMsgs.setQuitMoved(false);
			}
			
		}
	}

	@Override
	public boolean onClick(ISprite sprite) {
		return sprite.isVisible()
				&& (sprite.getCollisionBox().contains(mx, sy));
	}

	// �����½�����еļ��
	public String loginOnClick(Sprite sprite) {
		boolean b = sprite.isVisible()
				&& (sprite.getCollisionBox().contains(mx, sy));
		// ͨ������sp��sp2��ͼ�㲻ͬ�����жϵ��е����ĸ�sprite
		if (b && sprite.getLayer() == 1) {
			return sprite.getSpriteName();
		}
		return null;
	}

	private void sortSpites(LGraphics g) {

		this.allSprites.clear();
		this.allSprites.addAll(plants.allPlants);
		this.allSprites.addAll(zombies.allZombies);
		this.allSprites.addAll(Magic.allMagics);

		Collections.sort(this.allSprites, new Comparator<ObjectIn>() {
			@Override
			public int compare(ObjectIn s1, ObjectIn s2) {
				switch (s1.getObjectType()) {
				case ObjectIn.PLANT:
					s1 = (Plant) s1;
					break;
				case ObjectIn.BULLET:
					s1 = (Bullet) s1;
					break;
				case ObjectIn.MAGIC:
					s1 = (Magic) s1;
					break;
				}
				switch (s2.getObjectType()) {
				case ObjectIn.PLANT:
					s2 = (Plant) s2;
					break;
				case ObjectIn.BULLET:
					s2 = (Bullet) s2;
					break;
				case ObjectIn.MAGIC:
					s2 = (Magic) s2;
					break;
				}

				if (s1.getRowID() < s2.getRowID()) {
					return -1;
				} else if (s1.getRowID() >= s2.getRowID()) {
					return 1;
				} else {
					return compare(s1.getLayer(), s2.getLayer());
				}

			}

			private int compare(int s1, int s2) {
				if (s1 < s2) {
					return -1;
				} else if (s1 > s2) {
					return 1;
				} else {
					return 0;
				}
			}

		});
		for (int i = 0; i < this.allSprites.size(); i++) {
			Sprite s = (Sprite) this.allSprites.get(i);
			s.createUI(g);

		}
   
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 27){
			if(this.normalWindow){
				this.updateFullScreen();
				this.normalWindow=false;
			}else{
				this.updateNormalScreen();
				this.normalWindow=true;
			}
		}
		
		//�������ɾ����
		if(e.getKeyCode() == 8){
			if(this.openChatText){
			this.chatText.delete(this.chatText.getText().length()-1);
			}
		}
		
		//������»س�
		if(e.getKeyCode() == 10){
			if(!this.openChatText){
				chatText.setEnabled(true);
				chatText.setEditable(true);
				chatText.setVisible(true);	
				chatText.setFocusable(true);
				chatText.requestFocus();
				this.openChatText=true;
			}else{
				chatText.setVisible(false);
				this.openChatText=false;
				if(!chatText.getText().equals("")){
					ChatMsg cm = new ChatMsg(this.netID,chatNetID++,this.netName,chatText.getText());
					this.nc.send(cm);
					this.chatTextList.add(0,cm);
				}
				chatText.setText("");
			}
			
		}
	

	}

	public List<ChatMsg> getChatTextList() {
		return chatTextList;
	}
	
	
	private void initBrain(){
		Plant p1 =new Plant(136,true,10+mpx,100,plants);
		p1.setRowID(1);
		Plant p2 =new Plant(136,true,10+mpx,200,plants);
		p2.setRowID(2);
		Plant p3 =new Plant(136,true,10+mpx,300,plants);
		p3.setRowID(3);
		Plant p4 =new Plant(136,true,10+mpx,400,plants);
		p4.setRowID(4);
		Plant p5 =new Plant(136,true,10+mpx,500,plants);
		p5.setRowID(5);
		
		Plant p6 =new Plant(136,false,2350+mpx,100,plants);
		p6.setRowID(1);
		Plant p7 =new Plant(136,false,2350+mpx,200,plants);
		p7.setRowID(2);
		Plant p8 =new Plant(136,false,2350+mpx,300,plants);
		p8.setRowID(3);
		Plant p9 =new Plant(136,false,2350+mpx,400,plants);
		p9.setRowID(4);
		Plant p10 =new Plant(136,false,2350+mpx,500,plants);
		p10.setRowID(5);
	}
	
	public  void doGameOver(boolean isLeft, LGraphics g) {
			if(isLeft == this.isLeft){
				LImage lost = 	Resource.getLost() ;
				g.drawImage(lost, 100,100);
			}else{
				LImage win = 	Resource.getWin() ;
				g.drawImage(win, 100,100);
				
			}
		
	}
	
}
