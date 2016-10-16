import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.loon.framework.javase.game.action.sprite.Animation;
import org.loon.framework.javase.game.action.sprite.Sprite;
import org.loon.framework.javase.game.core.graphics.LFont;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;

public class NetMsgs {
	private static final int clx = 120, cy = 180, crx = 531;// ����½��Ϣ��λ��
	private int gly, gry; // ����½��Ϣ���¼��
	private Main main = null;
	 Sprite sp = new Sprite();
	 Sprite sp2 = new Sprite();
	private LImage actionFlag = Resource.getActionFlag();
	private LImage quit1 = Resource.getQuit1();
	private LImage quit2 = Resource.getQuit2();
	private boolean quitMoved ;//�����ƶ����˳���ťʱ��ͼƬ��ʾ
	private int temClick;// ��Ϊת������ͼƬ����������ʱ����
	List<NetMsg> netMsgs = new ArrayList<NetMsg>();
	private static final LFont l = new LFont("����", 0, 22);

	public NetMsgs(Main main) {
		this.main = main;
		sp.setAnimation(Animation.getDefaultAnimation("image\\action.png", 200,
				44, 10));
		sp.setLocation(300,120);
		sp.setSpriteName("sp");
		sp2.setAnimation(Animation.getDefaultAnimation(
				"image\\changeButton.png", 59, 53, 10));
		sp2.setLocation(380, 340);
		sp2.setSpriteName("sp2");
		main.add(sp);
		main.add(sp2);
	}

	// ����ת������İ�ť
	private void doButton(NetMsg c) {
		ClientTurnMsg msg = null;
		//����ת�����鰴ť
		
		if (main.loginOnClick(sp2)=="sp2") {
			if (!main.isLeft) {
				c.setLeft(true);
				msg = new ClientTurnMsg(true, c.isAction(), main);
			} else {
				c.setLeft(false);
				msg = new ClientTurnMsg(false, c.isAction(), main);
			}

		}
		//����ʼ��ť
		if (main.loginOnClick(sp)=="sp") {
			c.setAction(!c.isAction());
			Main.action = c.isAction();
			msg = new ClientTurnMsg(c.isLeft(), c.isAction(), main);
		}
		
		sp.setLayer(0);
		sp2.setLayer(0);
		
		
		if (null != msg)
			main.nc.send(msg);
	}
	
	private void checkRobot(){
		int left =0;
		int right=0;
		for(NetMsg nm : this.netMsgs){
			if(nm.isLeft()){
				left++;	
			}else{
				right++;
			}
		}
		if(left==0 || right == 0 ){
			Main.hasRobot=true;
		}
		
	}
	
	public void drawLoginString(LGraphics g) {
		gry = cy;
		gly = cy;
		g.setFont(l);
		boolean ok = true;
		// �ж��Ƿ�ʼ
		for (NetMsg nm : this.netMsgs) {
			if (!nm.isAction())
				ok = false;
		}
		if (ok) {
			this.checkRobot();
			main.remove(sp);
			main.remove(sp2);
			Main.chooseBegin = true;
		return;
		}
		
		//���˳�ͼƬ
		if(this.quitMoved){
			g.drawImage(quit2, 680, 20);
		}else{
			g.drawImage(quit1, 680, 20);	
		}
		
		for (int i = 0; i < netMsgs.size(); i++) {
			NetMsg c = netMsgs.get(i);
			// ����Ϣ
			if (c.isLeft()) {
				g.setColor(Color.BLACK);
				gly += 75;
				g.drawString(c.toString(), clx, gly+5);
				// ����ʼ���
				if(c.isAction()){
					g.drawImage(this.actionFlag,clx-79,gly-33);	
				}
			}

			if (!c.isLeft()) {
				gry += 75;
				Color t = g.getColor();
				g.setColor(Color.BLACK);
				g.drawString(c.toString(), crx, gry+5);
				// ����ʼ���
				if(c.isAction()){
					g.drawImage(this.actionFlag,crx-79,gry-33);	
				}
				g.setColor(t);

			}
			// ���ô�ʱmain�е�left����
			if (c.getNetID() == Main.netID) {
				// ����ť��Ϣ
				if (!c.isLeft()) {
					Main.isLeft = false;
				} else {
					Main.isLeft = true;
				}
				this.doButton(c);
				Main.action = c.isAction();
			}
			// �ж��ƶ�����ʼ��ť��ͼ����
			if (this.mouseMoved(sp, g)) {
				sp.setCurrentFrameIndex(1);
				g.drawImage(sp.getAnimation().getSpriteImage().getLImage(),
						(int) sp.getX(), (int) sp.getY());
			} else {
				sp.setCurrentFrameIndex(0);
			}

			// ����ת�����ͼƬ����
			this.temClick++;
			if (this.temClick == 1000) {
				if (sp2.getCurrentFrameIndex() == 1) {
					sp2.setCurrentFrameIndex(0);
					g.drawImage(sp.getAnimation().getSpriteImage().getLImage(),
							(int) sp.getX(), (int) sp.getY());
				} else {
					sp2.setCurrentFrameIndex(1);
					g.drawImage(sp.getAnimation().getSpriteImage().getLImage(),
							(int) sp.getX(), (int) sp.getY());
				}
				this.temClick =0;
			}

		}
		//����ʼ��ť��ͬ�� 
		synchronized (SynchronizedMsg.msgActionList) {
			Iterator<MsgActionSyn> ite = SynchronizedMsg.msgActionList.iterator();
			while (ite.hasNext()) {
				MsgActionSyn mas = (MsgActionSyn) ite.next();
				for(NetMsg nm : this.netMsgs){
					if(nm.getNetID()==mas.getMainID()){
						nm.setAction(mas.isAction());
						nm.setLeft(mas.isOnLeft());
						ite.remove();
					}
				}
			
		}
	}
	}
	

	
	
	private boolean mouseMoved(Sprite sp, LGraphics g) {
		return (main.sx >= sp.getX() && main.sx <= sp.getX() + sp.getWidth()
				&& main.sy >= sp.getY() && main.sy <= sp.getY()
				+ sp.getHeight());

	}

	public void setQuitMoved(boolean quitMoved) {
		this.quitMoved = quitMoved;
	}


	
	
	
	
	
	
}
