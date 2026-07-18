package com.projoker.smart_bazar.data;

import com.projoker.smart_bazar.Repository.RoleRepository;
import com.projoker.smart_bazar.Repository.UserRepository;
import com.projoker.smart_bazar.model.Role;
import com.projoker.smart_bazar.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles=Set.of("ROLE_ADMIN","ROLE_USER");
        createUserIfNotExist();
        createDefaultRoleIfNotExits(defaultRoles);
        createAdminIfNotExist();
    }

    private void createUserIfNotExist() {
        Role userRole=roleRepository.findByName("ROLE_USER").get();
        for(int i=1;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user=new User();
            user.setFirstName("The user");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Default vet user "+i+"created successfully.");
        }
    }


    private void createAdminIfNotExist() {
        Role adminRole=roleRepository.findByName("ROLE_ADMIN").get();
        for(int i=1;i<=2;i++){
            String defaultEmail="admin"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user=new User();
            user.setFirstName("Admin");
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Default admin user "+i+"created successfully.");
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream()
                .filter(role->roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }


}
