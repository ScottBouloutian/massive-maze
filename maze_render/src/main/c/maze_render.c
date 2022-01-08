#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

#define BITMAP_FILE_HEADER_SIZE 14
#define BITMAP_INFO_HEADER_SIZE 40
#define BITMAP_COLOR_TABLE_SIZE 8

unsigned char *getBitmapFileHeader(int);
unsigned char *getBitmapInfoHeader(int, int);
unsigned char *getBitmapColorTable();
void renderMaze(char *, char *);
bool readBit(FILE *);
void writeBit(FILE *, bool);

int main(int argc, char *argv[]) {
    if( argc == 3 ) {
        renderMaze(argv[1], argv[2]);
    } else {
        printf("Must specify input and output file\n");
    }
    printf("Program complete.\n");
}

unsigned char *getBitmapFileHeader(int size) {
    static unsigned char bitmapFileHeader[BITMAP_FILE_HEADER_SIZE] =
        {
            'B','M',  // header field
            0,0,0,0,  // the size of the BMP file in bytes
            0,0,      // reserved
            0,0,      // reserved
            62,0,0,0  // starting address of the image data
        };

    int i;

    // Set the size
    for(i=0;i<4;i++) {
        int b = size & 255;
        size = size >> 8;
        bitmapFileHeader[2+i] = b;
    }

    return bitmapFileHeader;
}

unsigned char *getBitmapInfoHeader(int width, int height) {
    static unsigned char bitmapInfoHeader[BITMAP_INFO_HEADER_SIZE] =
        {
            40,0,0,0, // the size of this header
            0,0,0,0,  // the bitmap width in pixels
            0,0,0,0,  // the bitmap height in pixels
            1,0,      // the number of color planes
            1,0,      // the number of bits per pixel
            0,0,0,0,  // the compression method being used
            0,0,0,0,  // the image size
            0,0,0,0,  // the horizontal resolution of the image
            0,0,0,0,  // the vertical resolution of the image
            0,0,0,0,  // the number of colors in the color palette
            0,0,0,0   // the number of important colors used
        };

    int i;

    // Set the width
    for(i=0;i<4;i++) {
        int b = width & 255;
        width = width >> 8;
        bitmapInfoHeader[4+i] = b;
    }

    // Set the height
    for(i=0;i<4;i++) {
        int b = height & 255;
        height = height >> 8;
        bitmapInfoHeader[8+i] = b;
    }

    return bitmapInfoHeader;
}

unsigned char *getBitmapColorTable() {
    static unsigned char colorTable[BITMAP_COLOR_TABLE_SIZE] =
        {
            0,0,0,0,      // black
            255,255,255,0 // white
        };
    return colorTable;
}

void renderMaze(char *inputFileName, char *outputFileName) {
    // Open the input file and check that it opened correctly
    FILE *inputFile = fopen(inputFileName, "rb");
    if (inputFile == NULL)
    {
        printf("Error opening file!\n");
        exit(1);
    }

    // Calculate the size of the input file
    fseek(inputFile, 0L, SEEK_END);
    long int inputFileSize = ftell(inputFile);
    fseek(inputFile, 0L, SEEK_SET);
    printf("The input file is %lu bytes.\n", inputFileSize);

    // Calculate the size of the maze
    int mazeSize = floor(sqrt(inputFileSize * 4));
    printf("The maze is of size %d.\n", mazeSize);

    // Calculate the size of the output image
    int imageSize = mazeSize * 2 + 1;

    // Calculate the size of the bitmap pixel array
    int pixelArrayRowSize = ceil(imageSize/32.0) * 4;
    long int pixelArraySize = pixelArrayRowSize * imageSize;
    printf("The pixel array is of size %lu.\n", pixelArraySize);

    // Calculate the size of the output file
    long int outputFileSize =
        BITMAP_FILE_HEADER_SIZE +
        BITMAP_INFO_HEADER_SIZE +
        BITMAP_COLOR_TABLE_SIZE +
        pixelArraySize;
    printf("The output file is %lu bytes.\n", outputFileSize);

    // Open the output file and check that it opened correctly
    FILE *outputFile = fopen(outputFileName, "wb");
    if (outputFile == NULL)
    {
        printf("Error opening file!\n");
        exit(1);
    }

    // Calculate the bitmap headers
    unsigned char *bitmapFileHeader = getBitmapFileHeader(outputFileSize);
    unsigned char *bitmapInfoHeader = getBitmapInfoHeader(imageSize, imageSize);
    unsigned char *bitmapColorTable = getBitmapColorTable();

    // Write the bitmap headers to the output file
    fwrite(bitmapFileHeader, 1, BITMAP_FILE_HEADER_SIZE, outputFile);
    fwrite(bitmapInfoHeader, 1, BITMAP_INFO_HEADER_SIZE, outputFile);
    fwrite(bitmapColorTable, 1, BITMAP_COLOR_TABLE_SIZE, outputFile);

    // It is now that we begin rendering the maze to the output file
    bool belowRow[mazeSize];
    bool aboveRow[mazeSize];
    bool currentRow[mazeSize];

    // Initialize the row arrays
    int i;
    for(i=0;i<mazeSize;i++) {
        belowRow[i]=false;
        aboveRow[i]=false;
        currentRow[i]=false;
    }
    belowRow[mazeSize-1] = true; // sets the exit pixel of the maze
    // Iterate through each direction in the maze structure file
    int y;
    int x;
    for(y=0;y<mazeSize;y++) {
        printf("Rendering row %d...\n", y+1);
        // Read the current row and store its directional information
        for(x=0;x<mazeSize;x++) {
            bool bi1 = readBit(inputFile);
            bool bi2 = readBit(inputFile);
            if(x==0 && y==mazeSize-1) {
                bi1=true;
                bi2=true;
            }
            if(bi1) {
                if(bi2) {
                    // down
                    aboveRow[x] = true;
                } else {
                    // up
                    belowRow[x] = true;
                }
            } else {
                if(bi2) {
                    // right
                    currentRow[x-1] = true;
                } else {
                    // left
                    currentRow[x] = true;
                }
            }
        }
        // Commit to the bitmap the below row
        for(x=0;x<mazeSize;x++) {
            writeBit(outputFile, false);
            if(belowRow[x] == true) {
                writeBit(outputFile, true);
            } else {
                writeBit(outputFile, false);
            }
        }
        writeBit(outputFile, false);
        // Pad the row
        for(i=imageSize;i<pixelArrayRowSize*8;i++) {
            writeBit(outputFile, false);
        }

        // Commit to the bitmap the current row
        writeBit(outputFile, false);
        for(x=0;x<mazeSize;x++) {
            writeBit(outputFile, true);
            if(currentRow[x]) {
                writeBit(outputFile, true);
            } else {
                writeBit(outputFile, false);
            }
        }
        // Pad the row
        for(i=imageSize;i<pixelArrayRowSize*8;i++) {
            writeBit(outputFile, false);
        }
        // Set the below row to the above row
        for(x=0;x<mazeSize;x++) {
            belowRow[x]=aboveRow[x];
        }
        // Clear the current and above rows
        for(i=0;i<mazeSize;i++) {
            aboveRow[i]=false;
            currentRow[i]=false;
        }
    }
    // Commit to the bitmap the last below row
    for(x=0;x<mazeSize;x++) {
        writeBit(outputFile, false);
        if(belowRow[x] == true) {
            writeBit(outputFile, true);
        } else {
            writeBit(outputFile, false);
        }
    }
    writeBit(outputFile, false);
    // Pad the row
    for(i=imageSize;i<pixelArrayRowSize*8;i++) {
        writeBit(outputFile, false);
    }

    // Close the files
    fclose(outputFile);
    fclose(inputFile);

}

bool readBit(FILE *file) {
    static unsigned char currentByte = 0;
    static unsigned char currentBit = 8;
    if(currentBit == 8) {
        currentByte = getc(file);
        currentBit = 0;
    }
    unsigned char temp = currentByte >> (7-currentBit);
    currentBit++;
    return temp%2;
}

void writeBit(FILE *file, bool bit) {
    static unsigned char currentByte = 0;
    static unsigned char currentBit = 0;
    if(bit) {
        currentByte++;
    }
    currentBit++;
    if (currentBit == 8) {
        putc(currentByte, file);
        currentByte = 0;
        currentBit = 0;
    } else {
        currentByte = currentByte << 1;
    }
}
