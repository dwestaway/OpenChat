package com.openchat.openchat

/**
 * Created by Dan on 14/03/2018.
 */

class Message {

    var content: String? = null
    var name: String? = null
    var time: String? = null

    constructor() {

    }

    constructor(content: String) {
        this.content = content

    }
}