/*
 * Misu.java
 *
 * Copyright (C) 2009-2010 Claudio Zopfi
 * 
 * Licensed under CC Attribution-Noncommercial-Share Alike 3.0 Germany
 * 
 * See the file license.txt which came with this distribution
 * or http://creativecommons.org/licenses/by-nc-sa/3.0/de/deed.en
 * or http://c1audio.com/by-nc-sa/
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 */
package org.misucatomisuco.control;

import org.misucatomisuco.view.MisuFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Misu {
	public static void main(String[] args) {
		
		String file="org/misucatomisuco/control/conf.xml";
		if(args.length>0) {
			file=args[0];
		}
		System.out.println("Opening conf file: "+file);
		ApplicationContext context =
		    new ClassPathXmlApplicationContext(file);
		context.getBean("frame", MisuFrame.class);
	}
}
