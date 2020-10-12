package com.redapp.tasker.services;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.redapp.tasker.dao.AppRoleRepository;
import com.redapp.tasker.dao.AppUsersRepository;
import com.redapp.tasker.dao.ResetTokenRepository;
import com.redapp.tasker.entities.AppRole;
import com.redapp.tasker.entities.AppUser;
import com.redapp.tasker.entities.PasswordRequest;
import com.redapp.tasker.entities.ResetToken;
import com.redapp.tasker.entities.UserRequest;

import lombok.NonNull;

@Service
@Transactional
public class UsersServiceImp implements UsersService {
	@Autowired
	private AppUsersRepository appUsersRepository;
	@Autowired
	private ResetTokenRepository resetTokenRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MailService mailService;
	@Autowired
	private Environment environment;

	@Override
	public List<AppUser> getUsers(String keyword, String state) {
		keyword = "%" + keyword + "%";
		if (state.equals("*"))
			state = "%%";
		return this.appUsersRepository.findByDetails(keyword, "USER", state);
	}

	@Override
	public List<AppUser> getManagers() {

		return this.appUsersRepository.findByDetails("%%", "MANAGER", "%%");
	}

	@Override
	public AppUser addUser(UserRequest userRequest) {
		if (this.appUsersRepository.existsById(userRequest.getUsername()))
			throw new RuntimeException("ce nom d'utilisateur existe Deja");
		AppUser user = new AppUser(userRequest.getUsername(),
				this.bCryptPasswordEncoder.encode(userRequest.getPassword()), userRequest.getName(),
				userRequest.getPhone(), userRequest.getMail(), userRequest.getAdress());
		AppRole role = this.appRoleRepository.findByRoleName("USER");
		user.setAppRole(role);
		return this.appUsersRepository.save(user);
		//this.mailService.sendEmail("Bonjour ", title, to, from);
	}

	@Override
	public AppUser editUser(String username, UserRequest userRequest) {

		Optional<AppUser> appUserOpt = this.appUsersRepository.findById(username);
		if (!appUserOpt.isPresent())
			throw new RuntimeException("Utilisateur Introuvable");
		AppUser appUser = appUserOpt.get();
		appUser.setName(userRequest.getName());
		appUser.setPhone(userRequest.getPhone());
		appUser.setMail(userRequest.getMail());
		appUser.setAdress(userRequest.getAdress());
		// appUser.setState(userRequest.getState());
		return appUser;
	}

	@Override
	public AppUser addManager(UserRequest userRequest) {
		AppUser user = this.addUser(userRequest);
		user.setState("ACTIVE");
		user.setAppRole(this.appRoleRepository.findByRoleName("MANAGER"));
		return user;
	}

	@Override
	public AppUser findByUsername(String username) {
		Optional<AppUser> appUserOpt = this.appUsersRepository.findById(username);
		if (!appUserOpt.isPresent())
			return null;
		return appUserOpt.get();
	}

	@Override
	public void deleteManager(String username) {
		AppUser manager = this.appUsersRepository.getOne(username);
		if (manager == null)
			throw new RuntimeException("Utilisateur introuvable");
		if (!manager.getAppRole().getRoleName().equals("MANAGER"))
			throw new RuntimeException("ce n'est pas un administrateur");
		appUsersRepository.delete(manager);

	}

	@Override
	public void deleteUser(String username) {
		AppUser user = this.appUsersRepository.getOne(username);
		if (user == null)
			throw new RuntimeException("Utilisateur introuvable");
		if (!user.getAppRole().getRoleName().equals("USER"))
			throw new RuntimeException("ce n'est pas un utilisateur");
		user.setState("DELETED");

	}

	@Override
	public void editPassword(String username, PasswordRequest passwordRequest) {
		System.out.println(passwordRequest.toString());
		Optional<AppUser> appUserOpt = this.appUsersRepository.findById(username);
		if (!appUserOpt.isPresent())
			throw new RuntimeException("Utilisateur introuvable");
		AppUser appUser = appUserOpt.get();
		// System.out.println(this.bCryptPasswordEncoder.matches(this.bCryptPasswordEncoder.encode("123456"),
		// "123456"));
		if (!this.bCryptPasswordEncoder.matches(passwordRequest.getOldpassword(), appUser.getPassword())) {
			throw new RuntimeException("Mot de passe incorrecte");
		}

		appUser.setPassword(this.bCryptPasswordEncoder.encode(passwordRequest.getPassword()));
		this.appUsersRepository.save(appUser);

	}

	@Override
	public AppUser getUser(String username) {
		Optional<AppUser> appUserOpt = this.appUsersRepository.findById(username);
		if (!appUserOpt.isPresent())
			throw new RuntimeException("Utilisateur introuvable");
		return appUserOpt.get();
	}

	@Override
	public void resetPassword(String username, String password) {
		AppUser user = this.getUser(username);
		ResetToken rt = new ResetToken(user, password);
		this.resetTokenRepository.save(rt);
		this.sendEmail(rt);

	}

	@Override
	public String confirmeResetPassword(Long id) {
		Optional<ResetToken> optTk = this.resetTokenRepository.findById(id);
		if (!optTk.isPresent())
			return "Demande introuvale";
		ResetToken tk = optTk.get();
		long diffInMillies = Math.abs(tk.getTokenDate().getTime() - new Date().getTime());
		long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		;
		if (diff > 48) {
			return "Delais de demande depassé, renouveller votre demande";
		} else {

			AppUser user = tk.getUser();
			user.setPassword(this.bCryptPasswordEncoder.encode(tk.getPassword()));
			this.appUsersRepository.save(user);
			this.resetTokenRepository.deleteById(tk.getId());
			return "Operation reussie vous pouvez vous authentifier avec votre nouveau mot de passe";
		}
	}

	private void sendEmail(ResetToken rt) {
try {
	String link=environment.getProperty("my.backend")+"/confirmeResetPassword?tokenId="+rt.getId();
	String body="<p><strong>Bonjour, pour confirmer la reinitialisation du mot de passe de votre compte Tasker&nbsp;<a href='"+link+"'>cliquez ici</a></strong></p>"+
	"<p>ou copier ce lien dans votre naviguateur</p>"+
	"<p>"+link+"</p>"+
	"<p>&nbsp;</p>"+
	"<p><span style='color: #ff0000;'>Nb: si vous n'avez pas demander de&nbsp;reinitialisation de votre mot de passe veuillez ignorer ce message</span></p>";
	
	this.mailService.sendEmail(body, "Reinitialisation de votre mot de passe", rt.getUser().getUsername(), "LetApp");
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}

}
