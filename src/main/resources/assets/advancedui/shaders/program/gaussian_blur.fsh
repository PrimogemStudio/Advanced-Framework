#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D InputSampler;

uniform vec4 ColorModulate;
uniform int Radius;

in vec2 texCoord;

out vec4 fragColor;


vec4 gaussian_blur() {
    vec4 color = vec4(0.0);
    int seg = Radius;
    int i = -seg;
    int j = 0;
    float f = 0.0f;
    float dv = 2.0f/512.0f;
    float tot = 0.0f;
    for(; i <= seg; ++i)
    {
        for(j = -seg; j <= seg; ++j)
        {
            f = (1.1 - sqrt(i*i + j*j)/8.0);
            f *= f;
            tot += f;
            color += texture(DiffuseSampler, vec2(texCoord.x + j * dv, texCoord.y + i * dv)).rgba * f;
        }
    }
    color /= tot;
    return color;
}

void main() {
    vec4 col = texture(InputSampler, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);
    if (col.a <= 0.01)
    {
        fragColor = dst * ColorModulate;
        return;
    }

    // fragColor = vec4(gaussian_blur(col), 1) * ColorModulate;
    fragColor = mix(gaussian_blur(), vec4(col.xyz, 1.0), col.a);
}
