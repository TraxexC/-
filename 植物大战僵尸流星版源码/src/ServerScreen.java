import java.awt.event.MouseEvent;

import org.loon.framework.javase.game.core.graphics.Screen;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;

public class ServerScreen extends Screen {
	static String localIP;
	static int clientNum=0;

	public ServerScreen() {

	}

	@Override
	public void draw(LGraphics g) {
		ServerScreen.clientNum =MainServer.clients.size();
		if (null != localIP) {
			g.drawString("���ip��ַ��:" + localIP, 50, 50);
			g.drawString("�Ѿ���" + clientNum + "����������", 50, 70);
		}
	
			

	}

	@Override
	public void leftClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void middleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rightClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
