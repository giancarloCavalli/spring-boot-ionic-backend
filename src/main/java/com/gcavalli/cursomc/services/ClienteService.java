package com.gcavalli.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gcavalli.cursomc.domain.Cidade;
import com.gcavalli.cursomc.domain.Cliente;
import com.gcavalli.cursomc.domain.Endereco;
import com.gcavalli.cursomc.domain.enums.Perfil;
import com.gcavalli.cursomc.domain.enums.TipoCliente;
import com.gcavalli.cursomc.dto.ClienteDTO;
import com.gcavalli.cursomc.dto.ClienteNewDTO;
import com.gcavalli.cursomc.repositories.CidadeRepository;
import com.gcavalli.cursomc.repositories.ClienteRepository;
import com.gcavalli.cursomc.repositories.EnderecoRepository;
import com.gcavalli.cursomc.security.UserSS;
import com.gcavalli.cursomc.services.exceptions.AuthorizationException;
import com.gcavalli.cursomc.services.exceptions.DataIntegrityException;
import com.gcavalli.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String imgNamePrefix;
	
	@Value("${img.profile.size}")
	private Integer imgSize;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId()))
			throw new AuthorizationException("Acesso negado");
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername()))
			throw new AuthorizationException("Acesso negado");
		Cliente obj;
		obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Objeto não encontrado");
		
		return obj;
	}
	
	public Cliente findByEmailNoAuthRequired(String email) {
		return repo.findByEmail(email);
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException dive) {
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos relacionados");
		}
	}

	public Cliente fromDto(ClienteDTO objDto) {
		Cliente obj = new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
		return obj;
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente obj = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
		
		obj.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null)
			obj.getTelefones().add(objDto.getTelefone2());
		if (objDto.getTelefone3() != null)
			obj.getTelefones().add(objDto.getTelefone3());
		
		Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), obj, cid.get());
		obj.getEnderecos().add(end);
		return obj;
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public URI UpdateProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null)
			throw new AuthorizationException("Acesso negado");
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, imgSize);
		String fileName = imgNamePrefix + user.getId() + ".jpg";
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image/jpeg");
	}

}
