package spring;

import java.util.Collection;

public class MemberListPinter {
    
	
	private MemberDao memberDao;
	private MemberPrinter printer;
	
	public MemberListPinter(MemberDao memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer = printer;
	}
	
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		for(Member m : members) {
			printer.print(m);
		}
	}
	
}
