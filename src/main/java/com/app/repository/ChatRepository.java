package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.modal.Chat;
import com.app.modal.User;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
	
	@Query("select c from Chat c join c.users u where u.id=:userId")
	public List<Chat> findChatByUserId(@Param("userId")Integer userId);
	
//	@Query("select c from Chat c where c.isGroup=false And :user Member of c.users And :reqUser Member of c.users")
//	public Chat findSingleChatByUserIds(@Param("user")User user, @Param("reqUser")User reqUser);
	
	@Query("select c from Chat c where c.isGroup = false and :user member of c.users and :reqUser member of c.users")
	public Chat findSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);


}
