package spring;

public class ChangePasswordService {
    
	private MemberDao memberDao;
	
	public ChangePasswordService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	
	public void changePassword(String email, String oldPwd, String newPwd) {
		
		//이메일을 사용하여 암호 변경
		Member member = memberDao.selecetByEmail(email);
		if(member == null)
		    throw new MemberNotFoundException();	
		
		member.changePassword(oldPwd, newPwd);
		
		memberDao.update(member);
	}
}
