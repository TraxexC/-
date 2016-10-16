
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.loon.framework.javase.game.action.sprite.Animation;
import org.loon.framework.javase.game.action.sprite.Sprite;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;
import org.loon.framework.javase.game.core.timer.LTimer;

public class Magic extends Sprite  implements ObjectIn{

	private int magicID;
	private int x, y;
	private int n;
	private int[] info;
	private Main main;
	private int playTimes = 1; // ���Ŵ���
	private int xr , yr ;// ����ͼƬ��x,y����ƫ��
	private int times;
	private int magicType; // ħ��������
	static final int HITED = 1;// ħ�����ͳ���
	static final int AOBUFF = 2;// ħ�����ͳ���
	static final int UPMAGIC = 3;// ħ�����ͳ���
	private int animationID; 
	private Animation animation = null;
	private int alpha=1;
	private static final int  infoStart=35; 
	private boolean doMapMove; // ��ħ���Ƿ���mapMove����
	private LTimer imageTimer ;
	private LTimer delayTimer ; //ħ�����ͷź�ĵȴ�ʱ��
	private int imageTime;
	private int custom1, custom2, custom3, custom4;
	private int fn;
	private boolean gc;
	private int rowID;
	private static final int  objectType = ObjectIn.MAGIC;
	public void setGc(boolean gc) {
		this.gc = gc;
	}

	public boolean isGc() {
		return gc;
	}

	LImage image = null;
	static List<Magic> allMagics = new ArrayList<Magic>();

	public Magic(int magicID, int x, int y, Main main) {
		this.setLayer(8);
		this.magicID = magicID;
		this.x = x;
		this.y = y;
		this.main = main;
		//this.x += Main.mpx; //���г�ʼλ������ȫ�����ⲿ���
		assignment();
		init();
	}

	private void init() {
		this.setAnimation(animation);
		fn = animation.getTotalFrames();
		allMagics.add(this);
		if(this.magicType == Magic.HITED || this.magicType==Magic.AOBUFF ){
			this.doMapMove =true;
		}
		imageTimer = new LTimer(this.imageTime);
	}

	private void assignment() {
		//��ʼ������ħ��
		if(this.magicID==601){
			this.animationID =10;
			animation = Resource.getMagicAnimation(animationID);
			this.magicType = Magic.UPMAGIC;
			this.imageTime = 30;
			return;
		}
		//��ʼ������ħ��
		
		this.info = Resource.getPlantInfo(magicID);
		this.animationID =info[infoStart+9];
		if(this.animationID==0)this.dead();
		animation = Resource.getMagicAnimation(animationID);
		this.magicType = info[infoStart];
		this.imageTime = info[infoStart+1];
		this.xr = info[infoStart+2];
		this.yr = info[infoStart+3];
		this.alpha = info[infoStart+4];
		this.custom1 = info[infoStart+5];
		this.custom2 = info[infoStart+6];
		this.custom3 = info[infoStart+7];
		this.custom4 = info[infoStart+8];
	}

	@Override
	public void createUI(LGraphics g) {
		g.setAlpha(this.alpha);
		g.drawImage(this.animation.getSpriteImage().getLImage(), x + xr,
						y + yr);
		g.setAlpha(1f);
	}
	@Override
	public void update(long timer) {
		if (this.imageTimer.action(timer)) {
			this.setCurrentFrameIndex(n);
			n++;
			//�������е�ħ������
			if(null != this.delayTimer&&this.delayTimer.action(timer)){
				this.dead();
			}
			if(this.magicType == Magic.HITED){
				if (n == fn) {
					n = fn;
					this.delayTimer = new LTimer(custom1);
				}
				return;
			}
			
			
			// �����޼�ϲ���
			if (this.magicType == Magic.AOBUFF) {
				if (n == fn) {
					n = 0;
				}
				return;
			}

			if (n == fn) {
				n = 0;
				times++;
				if (times == this.playTimes)
					dead();
			}
		}

	}

	public void dead() {
		this.gc = true;
	}
	public void mapMove(int reverse) {
		// ������ͼ�ƶ�ʱ��x
		if (this.doMapMove)
			x += reverse;
	}

	public static void mapMoveMagics(int reverse) {
		for (Magic m : Magic.allMagics) {
			m.mapMove(reverse);
		}
	}

	public static void doAllMagicsUpdate(long timer) {
		Iterator iter = Magic.allMagics.iterator();
		while (iter.hasNext()) {
			Magic m = (Magic) iter.next();
			if (m.isGc()) {
				iter.remove();
			} else {
				m.update(timer);
			}
		}

	}

	@Override
	public int getRowID() {
		return this.rowID;
	}

	@Override
	public int getObjectType() {
		return this.objectType;
	}

}
