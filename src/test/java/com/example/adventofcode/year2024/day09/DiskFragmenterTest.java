package com.example.adventofcode.year2024.day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiskFragmenterTest {

    private static Stream<Arguments> filepathsAndExpectedFilesystemChecksum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day09/example_input", 1928L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day09/input", 6299243228569L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFilesystemChecksum")
    void defragmentDisk(final String filename,
                        final long expectedFilesystemChecksum) throws IOException {
        assertEquals(expectedFilesystemChecksum, DiskFragmenter.defragmentDisk(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFilesystemChecksumDefragmentedByFiles() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day09/example_input", 2858L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day09/input", 6326952672104L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFilesystemChecksumDefragmentedByFiles")
    void defragmentDiskMoveFullFiles(final String filename,
                                     final long expectedFilesystemChecksum) throws IOException {
        assertEquals(expectedFilesystemChecksum, DiskFragmenter.defragmentDiskMoveFullFiles(filename));
    }
}