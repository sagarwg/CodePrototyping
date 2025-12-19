package com.mindcraft.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindcraft.dto.UserDto;
import com.mindcraft.entity.StockListEntity;
import com.mindcraft.entity.UserEntity;
import com.mindcraft.repository.StockRepository;
import com.mindcraft.repository.UserRepository;
import com.mindcraft.utility.ApiResponse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@WebServlet("/AllStocksUser")
public class UserController extends HttpServlet {
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private UserRepository userRepository;
	
	UserDto getDto = new UserDto();

	// Testing purpose
	@GetMapping("/userTest")
	public ResponseEntity<ApiResponse<String>> getTest() {
		String msg = "User Test Controller, Ok!";
		return ResponseEntity.ok(new ApiResponse<>("Test Passed", msg, true, null, HttpStatus.OK.value()));
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve user's name (for example, from the session)
		String username = (String) request.getSession().getAttribute("username");

		// If there's no username in the session, set a default message
		if (username == null) {
			username = "Guest";
		}

		// Set the username as a request attribute
		request.setAttribute("username", username);

		// Forward the request to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("AllStocksUser.jsp");
		dispatcher.forward(request, response);
	}

	// READ BY ID
	@PostMapping("/getUserLogin")
	public ResponseEntity<ApiResponse<Long>> getUserLogin(@RequestBody UserEntity userEntity) {
		logger.info("getUsername :"+userEntity.getUsername());
		logger.info("getPassword :"+userEntity.getPassword());
		try {
			Optional<UserEntity> opt = userRepository.findByUsername(userEntity.getUsername().trim());
			if (opt.isPresent()) {
				UserEntity user = opt.get();
				if (user.getPassword().equals(userEntity.getPassword().trim())) {
					getDto.setLoggedInUser(user.getUsername());
					getDto.setApiResponse("Ok");
					logger.info("id :" + user.getId());
					logger.info("username :" + user.getUsername());
					return ResponseEntity.ok(new ApiResponse<>("Ok", user.getId(), true, null, HttpStatus.OK.value()));
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Username/Password did not match", null, false, null,
							HttpStatus.UNAUTHORIZED.value()));
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ApiResponse<>("Username/Password did not match", null, false, null, HttpStatus.UNAUTHORIZED.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	// READ BY ID
	@GetMapping("/getUser/{id}")
	public ResponseEntity<ApiResponse<UserEntity>> getUserbyId(@PathVariable int id) {
		try {
			Optional<UserEntity> opt = userRepository.findById(id);
			if (opt.isPresent()) {
				return ResponseEntity
						.ok(new ApiResponse<>("Data retrieved", opt.get(), true, null, HttpStatus.OK.value()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Data not found", null, false, null, HttpStatus.NOT_FOUND.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}
}
