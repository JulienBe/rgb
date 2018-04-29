#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
const vec3 SEPIA = vec3(0.3, 0.25, 0.25); 

void main() {
	vec4 color = texture2D(u_texture, v_texCoords).rgba;
    float gray = (color.r + color.g + color.b) / 3.0;
    vec3 grayscale = vec3(gray);
    gl_FragColor = vec4(grayscale * SEPIA, color.a);
}         