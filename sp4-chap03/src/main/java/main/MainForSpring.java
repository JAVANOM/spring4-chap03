package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.AlreadyExistingMemberException;
import spring.ChangePasswordService;
import spring.IdPasswordNotMatchingException;
import spring.MemberInfoPrinter;
import spring.MemberListPinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.VersionPrinter;

public class MainForSpring {
	
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {
		
		// ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
        
		// 두 개 이상의 설정 xml 파일 사용
		ctx = new GenericXmlApplicationContext("classpath:conf1.xml", "classpath:conf2.xml");
		
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println("명령어를 입력하세요");
			String command = reader.readLine();
			if(command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break; //종료
			}
			if(command.startsWith("new")) {
				processNewCommand(command.split(" ")); // "boo:and:foo" REG = :  -> { "boo", "and", "foo" }} 
				continue;
			} else if(command.startsWith("change")) {
				processChangeCommand(command.split(" "));
				continue;
			} else if(command.equals("list")) {
				processListCommand();
				continue;
			} else if(command.startsWith("info")) {
				processInfoCommand(command.split(" "));
				continue;
			} else if (command.equals("version")){
				processVersionCommand();
				continue;
			}
			
			printHelp(); // 그 외 입력 시 해당 메서드 실행
		}
		
	}
	
	private static void processNewCommand(String[] arg) {
		if(arg.length != 5) {
			printHelp();
			return;
		}
		
		MemberRegisterService regSvc 
		    = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);
        
        if(!req.isPasswordEqualToConfirmPassowrd()) {
        	System.out.println("암호와 확인이 일치하지 않습니다.\n");
        	return;
        }
        
        try {
          regSvc.regist(req);
          System.out.println("등록했습니다.\n");
		} catch (AlreadyExistingMemberException e) {
			System.out.println("이미 존재하는 이메일 입니다.");
		}
	}
	
	private static void processChangeCommand(String[] arg) {
		if(arg.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService changePwdSvc
		 = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일 입니다.\n");
		} catch (IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}
	
	private static void processListCommand() {
		
		MemberListPinter listPrinter = 
		    ctx.getBean("listPrinter", MemberListPinter.class);
		listPrinter.printAll();
	}
	
	private static void processInfoCommand(String[] arg) {
		if(arg.length != 2) {
			printHelp();
			return;
		}
		MemberInfoPrinter infoPrinter =
		    ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(arg[1]);
	}
	
	private static void processVersionCommand() {
		VersionPrinter versionPrinter  = 
		   ctx.getBean("versionPrinter", VersionPrinter.class);
		versionPrinter.print();
	}
	

	
	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 다시 작성 해주세요!!");
	}
	

}
