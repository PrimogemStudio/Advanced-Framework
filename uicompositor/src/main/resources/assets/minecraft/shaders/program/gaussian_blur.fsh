#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D InputSampler;

uniform vec4 ColorModulate;
uniform int Radius;
uniform int DigType;
uniform float NoiseStrength;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

#define PI2 6.2831853072

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

vec4 blur_dig2(int samples) {
    float count = 1.0;
    vec4 color = texture(DiffuseSampler, texCoord);
    float directionStep = PI2 / 48;

    vec2 off;
    float c, s, dist, dist2, weight;
    for (float d = 0.0; d < PI2; d += directionStep) {
        c = cos(d);
        s = sin(d);
        dist = 1.0 / max(abs(c), abs(s));
        dist2 = dist * 3.0;
        off = vec2(c, s);
        for (float i = dist2; i <= 32.0; i += dist2) {
            weight = i / samples;
            count += weight;
            color += texture(DiffuseSampler, texCoord + off * oneTexel * i) * weight;
        }
    }

    return color / count;
}

float random(vec2 st) {
    return fract(sin(dot(st.xy,
    vec2(12.9898,78.233)))*
    43758.5453123) - 0.5;
}

void main() {
    vec4 col = texture(InputSampler, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);
    if (col.a <= 0.01)
    {
        fragColor = dst * ColorModulate;
        return;
    }

    fragColor = mix(DigType == 0 ? blur(Radius) : blur_dig2(Radius), vec4(col.xyz, 1.0), col.a);
    fragColor.x += random(texCoord) * NoiseStrength;
    fragColor.y += random(texCoord) * NoiseStrength;
    fragColor.z += random(texCoord) * NoiseStrength;
}
