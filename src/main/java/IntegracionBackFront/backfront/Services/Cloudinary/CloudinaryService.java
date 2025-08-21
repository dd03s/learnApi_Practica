package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    // 1. Definir el tamaño máximo de imagen en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // 2. Definir extensiones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    // 3. Atributo Cloudinary
    private final Cloudinary cloudinary;

    // Constructor para inyección de dependencia
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);

        // Subir imagen a Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Obtener URL de la imagen subida
        return uploadResult.get("secure_url").toString();
    }

    private void validateImage(MultipartFile file) {
        // 1. Verificar si el archivo está vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // 2. Verificar el tamaño del archivo
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");
        }

        // 3. Verificar el nombre del archivo
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("Nombre del archivo inválido");
        }

        // 4. Validar extensión del archivo
        boolean isValidExtension = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (originalFileName.toLowerCase().endsWith(ext)) {
                isValidExtension = true;
                break;
            }
        }

        if (!isValidExtension) {
            throw new IllegalArgumentException("Formato de archivo no soportado. Solo se permiten: .jpg, .jpeg, .png");
        }
    }
}
