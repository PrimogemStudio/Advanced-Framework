#version 150

in vec4 vertexColor;
in vec2 vertexUV;
in vec2 RawPosition;
in vec2 texCoord0;

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform int CalcSize;

out vec4 fragColor;

void main()
{
    fragColor = texture(Sampler0, texCoord0) * vec4(1, 1, 1, 1);
}