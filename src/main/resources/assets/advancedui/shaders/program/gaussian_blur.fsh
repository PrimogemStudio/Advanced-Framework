#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D InputSampler;

uniform vec4 ColorModulate;
uniform int Radius;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

vec4 blur(int samples) {
    vec4 O = vec4(0.0);
    float r = float(samples)*0.5;
    float sigma = r*0.5;
    float f = 1./(6.28318530718*sigma*sigma);

    int s2 = samples*samples;
    for (int i = 0; i<s2; i++) {
        vec2 d = vec2(i%samples, i/samples) - r;
        O += texture(DiffuseSampler, texCoord + oneTexel * d) * exp(-0.5 * dot(d/=sigma, d)) * f;
    }
    // use pre-multiplied alpha
    return O/O.a;
}

void main() {
    vec4 col = texture(InputSampler, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);
    if (col.a <= 0.01)
    {
        fragColor = dst * ColorModulate;
        return;
    }

    fragColor = mix(blur(Radius), vec4(col.xyz, 1.0), col.a);
}
