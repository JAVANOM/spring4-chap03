package spring;

public class MemberInfoPrinter {
    
	private MemberDao memDao;
	private MemberPrinter printer;
	
	//이 두 설정 메서드는 MemberDao 타입의 객체와 MemberPrinter 타입의 객체에 대한 의존 주입 
	public void setMemberDao(MemberDao memDao) {
		this.memDao = memDao;
	}

	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
	public void printMemberInfo(String email) {
		 Member member = memDao.selecetByEmail(email);
		 if(member == null) {
			 System.out.println("데이터 없음\n");
			 return;
		 }
		 printer.print(member);
		 System.out.println();
		 
		 
	}
	
	
	
}
