import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class main {

    public static void playBackground(String file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Sequencer sequencer = MidiSystem.getSequencer(); // Get the default Sequencer
                    if (sequencer == null) {
                        System.err.println("Sequencer device not supported");
                        return;
                    }
                    sequencer.open(); // Open device
                    // Create sequence, the File must contain MIDI file data.
                    Sequence sequence = MidiSystem.getSequence(new File(file));
                    sequencer.setSequence(sequence); // load it into sequencer
                    sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                    sequencer.start(); // start the playback
                } catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static void playBackground(String file, float bpm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Sequencer sequencer = MidiSystem.getSequencer(); // Get the default Sequencer
                    if (sequencer == null) {
                        System.err.println("Sequencer device not supported");
                        return;
                    }
                    sequencer.open(); // Open device
                    // Create sequence, the File must contain MIDI file data.
                    Sequence sequence = MidiSystem.getSequence(new File(file));
                    sequencer.setSequence(sequence); // load it into sequencer
                    sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                    sequencer.setTempoInBPM(bpm);
                    sequencer.start(); // start the playback
                } catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}