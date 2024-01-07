#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D InputSampler;

uniform vec4 ColorModulate;

in vec2 texCoord;

out vec4 fragColor;

uniform bool Horizontal;
uniform float Weight[5] = float[](0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);

vec3 gaussian_blur(vec4 col)
{
    vec2 tex_offset = 2.0 / textureSize(DiffuseSampler, 0);
    vec3 result = texture(DiffuseSampler, texCoord).rgb * Weight[0];
    if (Horizontal)
    {
        for (int i = 1; i < 5; ++i)
        {
            result += texture(DiffuseSampler, texCoord + vec2(tex_offset.x * i, 0.0)).rgb * Weight[i];
            result += texture(DiffuseSampler, texCoord - vec2(tex_offset.x * i, 0.0)).rgb * Weight[i];
        }
        result = col.xyz + result * (1 - col.a);
    }
    else
    {
        for (int i = 1; i < 5; ++i)
        {
            result += texture(DiffuseSampler, texCoord + vec2(0.0, tex_offset.y * i)).rgb * Weight[i];
            result += texture(DiffuseSampler, texCoord - vec2(0.0, tex_offset.y * i)).rgb * Weight[i];
        }
    }
    return result;
}

void main(){
    vec4 col = texture(InputSampler, texCoord);
    vec4 dst = texture(DiffuseSampler, texCoord);
    if (col.a <= 0.01)
    {
        fragColor = dst * ColorModulate;
        return;
    }

    fragColor = vec4(gaussian_blur(col), 1) * ColorModulate;
}
