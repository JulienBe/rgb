#ifdef GL_ES
    precision mediump float;
#endif
#define ray_density 4.5
#define gamma 5.

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

float noise( in vec2 x )
{
	return texture2D(u_texture, x*.01).x; // INCREASE MULTIPLIER TO INCREASE NOISE
}

// FLARING GENERATOR, A.K.A PURE AWESOME
mat2 m2 = mat2( 0.80,  0.60, -0.60,  0.80 );
float fbm( in vec2 p )
{	
	float z=2.;       // EDIT THIS TO MODIFY THE INTENSITY OF RAYS
	float rz = -0.05; // EDIT THIS TO MODIFY THE LENGTH OF RAYS
	p *= 0.25;        // EDIT THIS TO MODIFY THE FREQUENCY OF RAYS
	for (int i= 1; i < 6; i++)
	{
		rz+= abs((noise(p)-0.5)*2.)/z;
		z = z*2.;
		p = p*2.*m2;
	}
	return rz;
}

void main() {
        vec4 color = texture2D(u_texture, v_texCoords).rgba;
	        
	    float val;
	    float x = color.g;
	    float y = color.b;
	    
	    val = fbm(
		    vec2(color.r + y * ray_density,
		     color.r + x * ray_density));
	    val = smoothstep(gamma*.02-.1,ray_brightness+(gamma*0.02-.1)+.001,val);
	        
        vec3 col = val / vec3(color.r, color.g, color.b);
	        
        gl_FragColor = vec4(col, color.a);
}
