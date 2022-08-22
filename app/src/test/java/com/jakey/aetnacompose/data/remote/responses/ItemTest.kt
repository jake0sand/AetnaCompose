package com.jakey.aetnacompose.data.remote.responses

import com.jakey.aetnacompose.domain.detail_item.DetailItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class ItemTest {

    @Test
    fun `item mapper correctly maps to Detail Item`() {
        val testItem = Item(
            author = "nobody@flickr.com (\"Jake\")",
            media = Media(m = "https://www.youneverwalkalone.com/image.jpg"),
            title = "Sweet title, dude",
            description = "Thank you so much for this opportunity ):" +
                    "<p><a href=\"https://www.flickr.com/people/192004829@N02/\">focalphoto</a>" +
                    " posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/192004829@N02/" +
                    "52215631848/\" title=\"Aetna Health\"><img src=\"https://live.staticflickr.com/" +
                    "65535/52215631848_a92b441072_m.jpg\" width=\"240\" height=\"160\" alt=\"" +
                    "Aetna Health\" /></a></p> <p>Image of the Aetna Health app for iPhone. Take " +
                    "control of your health insurance.</p>"


        )

        val expected = DetailItem(
            author = "Jake",
            title = "Sweet title, dude",
            imageUrl = "https://www.youneverwalkalone.com/image.jpg",
            imageHeight = "160",
            imageWidth = "240",
            description = "Thank you so much for this opportunity ):" +
                    "<p><a href=\"https://www.flickr.com/people/192004829@N02/\">focalphoto</a>" +
                    " posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/192004829@N02/" +
                    "52215631848/\" title=\"Aetna Health\"><img src=\"https://live.staticflickr.com/" +
                    "65535/52215631848_a92b441072_m.jpg\" width=\"240\" height=\"160\" alt=\"" +
                    "Aetna Health\" /></a></p> <p>Image of the Aetna Health app for iPhone. Take " +
                    "control of your health insurance.</p>"
        )

        assertEquals(expected, testItem.toListImage())
    }

}